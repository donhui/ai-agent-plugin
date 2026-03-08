package io.jenkins.plugins.aiagentjob;

import hudson.Util;

import jenkins.model.Jenkins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds the CLI command line for each supported {@link AgentType}. Also handles environment
 * variable parsing and command-to-string serialisation.
 */
final class AiAgentCommandFactory {
    private AiAgentCommandFactory() {}

    static List<String> buildDefaultCommand(AiAgentConfiguration config, String prompt) {
        AiAgentTypeHandler handler = getHandlersByType().get(config.getAgentType());
        if (handler == null) {
            throw new IllegalStateException("Unsupported agent type: " + config.getAgentType());
        }
        List<String> command = new ArrayList<>(handler.buildDefaultCommand(config, prompt));

        String extraArgs = trimToNull(config.getExtraArgs());
        if (extraArgs != null) {
            Collections.addAll(command, Util.tokenize(extraArgs));
        }
        return command;
    }

    static Map<String, String> parseEnvironmentVariables(String raw) {
        Map<String, String> values = new LinkedHashMap<>();
        if (raw == null || raw.trim().isEmpty()) {
            return values;
        }

        String[] lines = raw.split("\\r?\\n");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            int idx = trimmed.indexOf('=');
            if (idx <= 0) {
                continue;
            }
            String key = trimmed.substring(0, idx).trim();
            String value = trimmed.substring(idx + 1);
            if (!key.isEmpty()) {
                values.put(key, value);
            }
        }
        return values;
    }

    static String commandAsString(List<String> command) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < command.size(); i++) {
            if (i > 0) {
                sb.append(' ');
            }
            String token = command.get(i);
            if (token.contains(" ") || token.contains("\"")) {
                sb.append('"').append(token.replace("\"", "\\\"")).append('"');
            } else {
                sb.append(token);
            }
        }
        return sb.toString();
    }

    private static Map<AgentType, AiAgentTypeHandler> getHandlersByType() {
        Map<AgentType, AiAgentTypeHandler> handlers = new HashMap<>();
        try {
            for (AiAgentTypeHandler handler :
                    Jenkins.get().getExtensionList(AiAgentTypeHandler.class)) {
                handlers.put(handler.getType(), handler);
            }
        } catch (IllegalStateException ignored) {
            // Tests that call this factory outside a Jenkins runtime fall back to built-in
            // handlers.
        }
        if (handlers.isEmpty()) {
            handlers.put(AgentType.CLAUDE_CODE, new BuiltinAiAgentTypeHandlers.ClaudeCodeHandler());
            handlers.put(AgentType.CODEX, new BuiltinAiAgentTypeHandlers.CodexHandler());
            handlers.put(
                    AgentType.CURSOR_AGENT, new BuiltinAiAgentTypeHandlers.CursorAgentHandler());
            handlers.put(AgentType.OPENCODE, new BuiltinAiAgentTypeHandlers.OpenCodeHandler());
            handlers.put(AgentType.GEMINI_CLI, new BuiltinAiAgentTypeHandlers.GeminiCliHandler());
        }
        return handlers;
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
