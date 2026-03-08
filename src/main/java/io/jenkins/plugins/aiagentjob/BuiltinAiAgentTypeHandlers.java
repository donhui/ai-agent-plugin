package io.jenkins.plugins.aiagentjob;

import hudson.Extension;

import java.util.ArrayList;
import java.util.List;

/** Built-in implementations for known AI agent CLIs. */
final class BuiltinAiAgentTypeHandlers {
    private BuiltinAiAgentTypeHandlers() {}

    @Extension
    public static final class ClaudeCodeHandler implements AiAgentTypeHandler {
        @Override
        public AgentType getType() {
            return AgentType.CLAUDE_CODE;
        }

        @Override
        public List<String> buildDefaultCommand(AiAgentConfiguration config, String prompt) {
            List<String> command = new ArrayList<>();
            command.add("npx");
            command.add("-y");
            command.add("@anthropic-ai/claude-code");
            command.add("-p");
            command.add(prompt);
            command.add("--output-format=stream-json");
            command.add("--verbose");
            if (config.isYoloMode()) {
                command.add("--dangerously-skip-permissions");
            } else if (config.isRequireApprovals()) {
                command.add("--permission-mode=default");
            }
            String model = trimToNull(config.getModel());
            if (model != null) {
                command.add("--model");
                command.add(model);
            }
            return command;
        }
    }

    @Extension
    public static final class CodexHandler implements AiAgentTypeHandler {
        @Override
        public AgentType getType() {
            return AgentType.CODEX;
        }

        @Override
        public List<String> buildDefaultCommand(AiAgentConfiguration config, String prompt) {
            List<String> command = new ArrayList<>();
            command.add("codex");
            command.add("exec");
            command.add("--json");
            command.add("--skip-git-repo-check");
            if (config.isYoloMode()) {
                command.add("--dangerously-bypass-approvals-and-sandbox");
            } else {
                command.add("--sandbox");
                command.add("workspace-write");
                command.add("--full-auto");
            }
            String model = trimToNull(config.getModel());
            if (model != null) {
                command.add("--model");
                command.add(model);
            }
            command.add(prompt);
            return command;
        }
    }

    @Extension
    public static final class CursorAgentHandler implements AiAgentTypeHandler {
        @Override
        public AgentType getType() {
            return AgentType.CURSOR_AGENT;
        }

        @Override
        public List<String> buildDefaultCommand(AiAgentConfiguration config, String prompt) {
            List<String> command = new ArrayList<>();
            command.add("agent");
            command.add("-p");
            command.add("--output-format=stream-json");
            command.add("--trust");
            command.add("--approve-mcps");
            if (config.isYoloMode()) {
                command.add("--yolo");
            }
            String model = trimToNull(config.getModel());
            if (model != null) {
                command.add("--model");
                command.add(model);
            }
            command.add(prompt);
            return command;
        }
    }

    @Extension
    public static final class OpenCodeHandler implements AiAgentTypeHandler {
        @Override
        public AgentType getType() {
            return AgentType.OPENCODE;
        }

        @Override
        public List<String> buildDefaultCommand(AiAgentConfiguration config, String prompt) {
            List<String> command = new ArrayList<>();
            command.add("opencode");
            command.add("run");
            command.add("--format");
            command.add("json");
            String model = trimToNull(config.getModel());
            if (model != null) {
                command.add("--model");
                command.add(model);
            }
            command.add(prompt);
            return command;
        }
    }

    @Extension
    public static final class GeminiCliHandler implements AiAgentTypeHandler {
        @Override
        public AgentType getType() {
            return AgentType.GEMINI_CLI;
        }

        @Override
        public List<String> buildDefaultCommand(AiAgentConfiguration config, String prompt) {
            List<String> command = new ArrayList<>();
            command.add("gemini");
            command.add("-p");
            command.add(prompt);
            command.add("--output-format");
            command.add("stream-json");
            if (config.isYoloMode()) {
                command.add("--yolo");
            } else if (config.isRequireApprovals()) {
                command.add("--approval-mode");
                command.add("default");
            }
            String model = trimToNull(config.getModel());
            if (model != null) {
                command.add("-m");
                command.add(model);
            }
            return command;
        }
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
