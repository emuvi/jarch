import base64
import os
import sys
import time
import requests

# ==========================================
# CONFIGURATION
# ==========================================
# It's best practice to set these via environment variables
TOKEN_USERNAME = os.getenv("SONATYPE_TOKEN_USERNAME", "your_token_username")
TOKEN_PASSWORD = os.getenv("SONATYPE_TOKEN_PASSWORD", "your_token_password")

# The path to your pre-packaged jarch bundle (e.g., target/central-publishing/central-bundle.zip)
BUNDLE_PATH = "path/to/jarch-bundle.zip"
DEPLOYMENT_NAME = "jarch-release"
# Choose 'AUTOMATIC' to publish immediately after passing validation,
# or 'USER_MANAGED' to manually review in the portal UI.
PUBLISHING_TYPE = "AUTOMATIC" 

BASE_URL = "https://central.sonatype.com/api/v1/publisher"

def generate_auth_header(username, password):
    """Generates the required base64 encoded Bearer token."""
    credentials = f"{username}:{password}"
    encoded = base64.b64encode(credentials.encode("utf-8")).decode("utf-8")
    return {"Authorization": f"Bearer {encoded}"}

def upload_bundle(auth_header, file_path, name, pub_type):
    """Uploads the bundle archive to Sonatype Central."""
    url = f"{BASE_URL}/upload"
    params = {
        "name": name,
        "publishingType": pub_type
    }
    
    if not os.path.exists(file_path):
        print(f"Error: Bundle file not found at {file_path}")
        sys.exit(1)
        
    print(f"Uploading {file_path} to Sonatype Central...")
    with open(file_path, "rb") as f:
        files = {
            "bundle": (os.path.basename(file_path), f, "application/octet-stream")
        }
        response = requests.post(url, headers=auth_header, params=params, files=files)
        
    if response.status_code == 201:
        deployment_id = response.text.strip()
        print(f"Upload successful! Deployment ID: {deployment_id}")
        return deployment_id
    else:
        print(f"Upload failed with status code {response.status_code}")
        print(response.text)
        sys.exit(1)

def check_status(auth_header, deployment_id):
    """Checks the validation and publishing status of the deployment."""
    url = f"{BASE_URL}/status"
    params = {"id": deployment_id}
    
    response = requests.post(url, headers=auth_header, params=params)
    if response.status_code == 200:
        return response.json()
    else:
        print(f"Failed to fetch status: {response.status_code}")
        print(response.text)
        return None

def monitor_deployment(auth_header, deployment_id):
    """Polls the status API until deployment reaches a final state."""
    print("\nMonitoring deployment status...")
    while True:
        status_data = check_status(auth_header, deployment_id)
        if not status_data:
            time.sleep(10)
            continue
            
        state = status_data.get("deploymentState")
        print(f"Current State: {state}")
        
        if state == "PUBLISHED":
            print("\n🎉 Success! jarch has been successfully published to Maven Central.")
            print(f"Package URLs: {status_data.get('purls')}")
            break
        elif state == "VALIDATED" and PUBLISHING_TYPE == "USER_MANAGED":
            print("\n✅ Validation passed! Since this is USER_MANAGED, log into the portal to release it.")
            break
        elif state == "FAILED":
            print("\n❌ Deployment Failed. Details:")
            print(status_data.get("errors", "No specific error provided."))
            sys.exit(1)
        elif state in ["PENDING", "VALIDATING", "PUBLISHING"]:
            # Keep polling every 10 seconds
            time.sleep(10)
        else:
            print(f"Unknown state: {state}")
            time.sleep(10)

def main():
    if TOKEN_USERNAME == "your_token_username" or TOKEN_PASSWORD == "your_token_password":
        print("Error: Please update the script with your real Sonatype token credentials.")
        sys.exit(1)
        
    headers = generate_auth_header(TOKEN_USERNAME, TOKEN_PASSWORD)
    deployment_id = upload_bundle(headers, BUNDLE_PATH, DEPLOYMENT_NAME, PUBLISHING_TYPE)
    monitor_deployment(headers, deployment_id)

if __name__ == "__main__":
    main()