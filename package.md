# Preparing the `jarch` Bundle for Maven Central

To publish the `jarch` project to the Sonatype Central Repository, you must submit a deployment bundle that meets a strict set of requirements. The bundle must follow the exact Maven Repository Layout folder convention (e.g., `com/vidlus/jarch/<version>/`) and contain your compiled code, Javadoc, source code, POM metadata, cryptographic signatures, and checksums. The final archive must be a `.zip` or `.tar.gz` file under 1GB in size.

Here is a comprehensive guide to setting up your environment and preparing this bundle.

---

## 1. Prerequisites and Installations

Before generating the bundle, you need a few standard development tools installed on your machine.

*   **Java Development Kit (JDK):** Required to compile `jarch`.
    *   *Download:* [Adoptium (Eclipse Temurin)](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
*   **Apache Maven (`mvn`):** Used to build the project and package the base `.jar` files.
    *   *Download:* [Maven Download Page](https://maven.apache.org/download.cgi)
*   **GnuPG (`gpg`):** Required to cryptographically sign every file in your release.
    *   *Download:* [GnuPG Download Page](https://www.gnupg.org/download/)
*   **Python 3:** Required to run the `package.py` automation script.
    *   *Download:* [Python Download Page](https://www.python.org/downloads/)

---

## 2. GPG Key Setup

All deployed files must be signed with GPG/PGP, and an `.asc` signature file must be included for each one. Note that `.asc` files themselves do not need checksums, and checksum files do not need `.asc` signatures.

If you haven't set up GPG yet, follow these steps:

1.  **Generate a Key Pair:**
    Open your terminal and run:
    ```bash
    gpg --gen-key
    ```
    Follow the prompts to provide your real name and email address[cite: 2]. Secure your key with a strong passphrase[cite: 2].
2.  **Find Your Key ID:**
    List your keys to find your Key ID (a long alphanumeric string):
    ```bash
    gpg --list-keys
    ```
3.  **Distribute Your Public Key:**
    Other developers and the Sonatype servers need your public key to verify your files. Distribute it to a supported keyserver:
    ```bash
    gpg --keyserver keyserver.ubuntu.com --send-keys <YOUR_KEY_ID>
    ```
    *Note: Central Servers support `keyserver.ubuntu.com`, `keys.openpgp.org`, and `pgp.mit.edu`.*

---

## 3. Exporting Certificates from Kleopatra to GPG CLI

If you are using Kleopatra (via Gpg4win) on Windows, the Kleopatra GUI and the Windows `gpg` command-line tool actually share the same keyring by default. If you run `gpg --list-keys` in your Windows command prompt, your Kleopatra keys will automatically appear. 

However, if you need to export your certificates from Kleopatra to use the `gpg` command-line tool in a different environment (such as WSL, a Linux VM, or a different machine), follow these exact steps:

1. Open Kleopatra and click on the certificate you want to export.
2. Right-click the certificate and select **Backup Secret Certificates** to export your private key.
3. Choose a safe location, provide your passphrase, and save the file (e.g., `private-key.asc`).
4. Right-click the certificate again and select **Export** to save your public key.
5. Save this file to the same location (e.g., `public-key.asc`).
6. Open your terminal in the target environment (e.g., WSL or Linux terminal).
7. Import the public key by running: `gpg --import public-key.asc`
8. Import the private key by running: `gpg --import private-key.asc`
9. Verify the keys are securely imported by running: `gpg --list-secret-keys`

---

## 4. Configure Required `pom.xml` Metadata

Sonatype strictly enforces minimum metadata requirements to ensure quality and allow users to find relevant details about your component. Ensure your `pom.xml` includes the following:

*   **Correct Coordinates:** `groupId` (e.g., `com.vidlus`), `artifactId` (`jarch`), and `version`. The version cannot end in `-SNAPSHOT` for a final release.
*   **Project Info:** `<name>`, `<description>`, and `<url>`.
*   **License Information:** You must declare the license(s) used for distributing your components.
*   **Developer Information:** Include a `<developers>` section with your name, email, organization, and organization URL. (A link to a GitHub profile is acceptable if you don't have an organization website).
*   **SCM (Source Control Management):** You must include `<connection>`, `<developerConnection>`, and `<url>` linking to your source control. The URLs do not actually have to be publicly accessible, but the elements must be present.

---

## 5. Run the Preparation Script

Once your environment is set up and your `pom.xml` is configured, use the `package.py` script provided previously. Place it in the root of your `jarch` project (next to the `pom.xml`) and run:

```bash
python package.py
```
