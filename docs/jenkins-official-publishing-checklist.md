# Jenkins Official Publishing Checklist

This checklist tracked the migration of this plugin from a personal repository to official
Jenkins distribution. The repository now lives at `jenkinsci/ai-agent-plugin`.

## Locked Decisions

- Plugin ID (`artifactId`): `ai-agent` (final, must not change after official publication).
- `jenkinsci` repository: `ai-agent-plugin`.
- Previous source repository (deleted): `https://github.com/bvolpato/jenkins-ai-agent-plugin`.

## 1. Pre-Hosting Readiness ✅

- [x] License is present and declared in `pom.xml`.
- [x] Jenkinsfile is present for Jenkins CI.
- [x] CI is green on Java 17 and Java 21.
- [x] Plugin metadata uses stable ID and Jenkins baseline.
- [x] Security policy and contribution docs are present.

## 2. Hosting Request ✅

- [x] Hosting request opened and accepted.

## 3. Repository Permissions Updater (RPU) ✅

- [x] RPU file exists with `github: "jenkinsci/ai-agent-plugin"`.
- [x] `developers` contains at least one Jenkins account ID.
- [x] `cd.enabled: true` is present.

## 4. Post-Transfer Repository Update ✅

- [x] `pom.xml` `<url>` points to `https://github.com/jenkinsci/ai-agent-plugin`.
- [x] README badge/link URLs updated from `bvolpato/jenkins-ai-agent-plugin` to `jenkinsci/ai-agent-plugin`.
- [x] Plugin ID remains `ai-agent`.

## 5. Enable Jenkins CD Workflow ✅

- [x] `.github/workflows/cd.yaml` is present.

## 6. First Official Release

- [ ] Merge a PR with a release-triggering label (`bug`, `enhancement`, or `developer`) or run `cd.yaml` manually.
- [ ] Confirm GitHub Actions CD run is successful.
- [ ] Confirm release appears on `https://plugins.jenkins.io/ai-agent/`.
- [ ] Confirm update center metadata shows the released version.

## 7. Cleanup and Transition

- [x] Retire personal-repo `release.yml` flow to avoid multiple release paths.
- [x] Delete personal repository `bvolpato/jenkins-ai-agent-plugin`.
- [x] Update all references to point to `jenkinsci/ai-agent-plugin`.
- [ ] Keep `main` on next `-SNAPSHOT` after each release.
