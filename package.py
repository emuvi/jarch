import os
import subprocess
import hashlib
import zipfile
import shutil
import xml.etree.ElementTree as ET
from pathlib import Path

# ==========================================
# CONFIGURATION
# ==========================================
POM_FILE = "pom.xml"
STAGING_DIR = "target/central-staging"
BUNDLE_NAME = "target/central-bundle.zip"

def get_pom_metadata():
    """Parses the pom.xml to extract groupId, artifactId, and version."""
    print("Parsing pom.xml...")
    tree = ET.parse(POM_FILE)
    root = tree.getroot()
    
    # Handle maven XML namespaces
    ns = {'mvn': 'http://maven.apache.org/POM/4.0.0'}
    
    group_id = root.findtext('mvn:groupId', namespaces=ns)
    # Sometimes groupId is inherited from a parent
    if not group_id:
        group_id = root.findtext('mvn:parent/mvn:groupId', namespaces=ns)
        
    artifact_id = root.findtext('mvn:artifactId', namespaces=ns)
    version = root.findtext('mvn:version', namespaces=ns)
    if not version:
        version = root.findtext('mvn:parent/mvn:version', namespaces=ns)
        
    return group_id, artifact_id, version

def run_maven_build():
    """Compiles the project and generates sources and javadoc jars."""
    print("Running Maven build (compiling, generating sources and javadoc)...")
    # Using standard maven plugins to generate the required artifacts
    cmd = ["mvn", "clean", "package", "source:jar", "javadoc:jar"]
    result = subprocess.run(cmd, capture_output=True, text=True)
    if result.returncode != 0:
        print("Maven build failed!")
        print(result.stdout)
        print(result.stderr)
        exit(1)
    print("Maven build completed successfully.")

def generate_checksum(file_path, algo):
    """Generates a hex checksum for a given file."""
    hash_func = hashlib.md5() if algo == 'md5' else hashlib.sha1()
    with open(file_path, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            hash_func.update(chunk)
    return hash_func.hexdigest()

def sign_file_with_gpg(file_path):
    """Signs a file using GPG, creating an .asc file."""
    print(f"Signing {os.path.basename(file_path)}...")
    cmd = ["gpg", "--armor", "--detach-sign", str(file_path)]
    subprocess.run(cmd, check=True)

def prepare_staging_area(group_id, artifact_id, version):
    """Creates the layout required by Maven Central and copies files over."""
    # Convert groupId to path (e.g., com.vidlus.jarch -> com/vidlus/jarch)
    group_path = group_id.replace('.', '/')
    target_path = Path(STAGING_DIR) / group_path / artifact_id / version
    
    if target_path.exists():
        shutil.rmtree(target_path)
    target_path.mkdir(parents=True, exist_ok=True)
    
    # Identify generated artifacts
    base_name = f"{artifact_id}-{version}"
    source_dir = Path("target")
    
    files_to_stage = [
        (Path(POM_FILE), f"{base_name}.pom"),
        (source_dir / f"{base_name}.jar", f"{base_name}.jar"),
        (source_dir / f"{base_name}-sources.jar", f"{base_name}-sources.jar"),
        (source_dir / f"{base_name}-javadoc.jar", f"{base_name}-javadoc.jar")
    ]
    
    staged_files = []
    
    for src_file, dest_name in files_to_stage:
        if not src_file.exists():
            print(f"Warning: Expected artifact {src_file} not found. Skipping.")
            continue
            
        dest_file = target_path / dest_name
        shutil.copy2(src_file, dest_file)
        staged_files.append(dest_file)
        
    return staged_files, target_path

def create_signatures_and_checksums(staged_files):
    """Generates .asc, .md5, and .sha1 for all staged files."""
    for file_path in staged_files:
        # 1. GPG Signature (.asc)
        sign_file_with_gpg(file_path)
        
        # 2. Checksums (.md5, .sha1)
        for algo in ['md5', 'sha1']:
            checksum = generate_checksum(file_path, algo)
            checksum_file = Path(f"{file_path}.{algo}")
            with open(checksum_file, "w") as f:
                f.write(checksum)
            print(f"Generated {algo} for {os.path.basename(file_path)}")

def create_bundle(staging_dir, output_bundle):
    """Zips the staging directory into a bundle ready for upload."""
    print(f"Creating bundle at {output_bundle}...")
    with zipfile.ZipFile(output_bundle, 'w', zipfile.ZIP_DEFLATED) as zipf:
        for root, _, files in os.walk(staging_dir):
            for file in files:
                file_path = Path(root) / file
                # Calculate the relative path inside the zip
                arcname = file_path.relative_to(staging_dir)
                zipf.write(file_path, arcname)
    print("Bundle created successfully!")

def main():
    if not Path(POM_FILE).exists():
        print("Run this script from the root of the jarch project (where pom.xml is located).")
        exit(1)
        
    group_id, artifact_id, version = get_pom_metadata()
    print(f"Detected Artifact: {group_id}:{artifact_id}:{version}")
    
    run_maven_build()
    
    staged_files, target_path = prepare_staging_area(group_id, artifact_id, version)
    
    print("\nGenerating signatures and checksums...")
    create_signatures_and_checksums(staged_files)
    
    create_bundle(STAGING_DIR, BUNDLE_NAME)
    
    print(f"\n✅ Done! You can now upload '{BUNDLE_NAME}' to the Sonatype Central Publisher Portal.")

if __name__ == "__main__":
    main()