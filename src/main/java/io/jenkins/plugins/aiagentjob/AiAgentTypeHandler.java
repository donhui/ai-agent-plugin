package io.jenkins.plugins.aiagentjob;

import hudson.ExtensionPoint;

import java.util.List;

/** Extension point that defines command construction and defaults for a supported AI agent type. */
public interface AiAgentTypeHandler extends ExtensionPoint {
    AgentType getType();

    List<String> buildDefaultCommand(AiAgentConfiguration config, String prompt);
}
