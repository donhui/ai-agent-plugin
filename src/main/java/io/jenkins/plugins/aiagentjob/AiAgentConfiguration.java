package io.jenkins.plugins.aiagentjob;

/** Shared execution settings used by the AI agent builder step and command execution flow. */
interface AiAgentConfiguration {
    AgentType getAgentType();

    String getModel();

    String getPrompt();

    String getWorkingDirectory();

    boolean isYoloMode();

    boolean isRequireApprovals();

    int getApprovalTimeoutSeconds();

    String getCommandOverride();

    String getExtraArgs();

    String getEnvironmentVariables();

    boolean isFailOnAgentError();

    String getSetupScript();

    boolean isCodexCustomConfigEnabled();

    String getCodexCustomConfigToml();

    String getApiCredentialsId();

    String getEffectiveApiKeyEnvVar();
}
