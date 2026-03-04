# Jenkins Official Publishing Checklist

This checklist tracks the steps to move this plugin from personal GitHub releases to official Jenkins distribution.

## 0. Finalize Identity (Blocking)

- [x] Confirm final plugin ID (`artifactId`) before first official release: `ai-agent-job`.
- [ ] Confirm GitHub repo name to be hosted in `jenkinsci`.
- [x] Confirm display name and short description are final.

Notes:
- The plugin ID is effectively permanent once officially published.
- Prefer avoiding `jenkins` and `plugin` words in `artifactId` unless required.

## 1. Repository Hygiene

- [x] Add root `LICENSE` file.
- [x] Keep `pom.xml` license metadata.
- [x] Add root `Jenkinsfile` for `ci.jenkins.io`.
- [x] Verify README has usage, configuration, and limitations.
- [x] Add SECURITY.md (recommended).

## 2. Request Hosting in `jenkinsci`

- [ ] Open a hosting request in:
      `https://github.com/jenkins-infra/repository-permissions-updater/issues/new/choose`
- [ ] Reference this repository and maintainers.
- [ ] Wait for infra team response about transfer/fork into `jenkinsci`.

Suggested issue summary:
- `Hosting request for <plugin-name> Jenkins plugin`

Suggested issue details:
- Plugin repository URL.
- Short plugin description.
- Jenkins Jira ID (if available).
- Maintainer GitHub IDs.
- Confirmation that this is intended for official Jenkins distribution.

## 3. Release Permissions (RPU)

- [ ] Add/merge repository permissions YAML in:
      `https://github.com/jenkins-infra/repository-permissions-updater`
- [ ] Include deployer IDs under your plugin artifact path.
- [ ] Verify Artifactory deploy permission is granted.

## 4. CD Setup for Jenkins Releases

- [ ] Add Jenkins plugin CD workflow (`cd.yaml`) after permission is ready.
- [ ] Ensure required secrets are present (`MAVEN_USERNAME`, `MAVEN_TOKEN`).
- [ ] Keep GitHub release notes workflow optional; official publication should flow through Jenkins release automation.

## 5. First Official Release

- [ ] Tag from the `jenkinsci/<repo>` repository.
- [ ] Confirm GitHub Action/Jenkins release pipeline succeeds.
- [ ] Confirm plugin appears on `https://plugins.jenkins.io/`.
- [ ] Confirm update center metadata includes the new version.

## 6. Post-Release

- [ ] Announce plugin availability.
- [ ] Document upgrade path from pre-official versions.
- [ ] Monitor issues and user feedback from early adopters.
