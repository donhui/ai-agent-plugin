package io.jenkins.plugins.aiagentjob.opencode;

import io.jenkins.plugins.aiagentjob.AgentUsageStats;
import io.jenkins.plugins.aiagentjob.AiAgentStatsExtractor;

import net.sf.json.JSONObject;

import java.util.Locale;

/**
 * Stats extractor for OpenCode JSONL output. OpenCode reports per-step usage in "step_finish"
 * events with a nested "part" object containing cost, tokens, and cache data. Values are
 * accumulated additively across multiple steps.
 */
public final class OpenCodeStatsExtractor implements AiAgentStatsExtractor {

    public static final OpenCodeStatsExtractor INSTANCE = new OpenCodeStatsExtractor();

    private OpenCodeStatsExtractor() {}

    @Override
    public boolean extract(JSONObject json, AgentUsageStats stats) {
        String type = json.optString("type", "").toLowerCase(Locale.ROOT);

        if ("step_finish".equals(type)) {
            JSONObject part = json.optJSONObject("part");
            if (part != null) {
                extractOpenCodePart(part, stats);
            }
            return true;
        }

        return false;
    }

    private void extractOpenCodePart(JSONObject part, AgentUsageStats stats) {
        double partCost = part.optDouble("cost", 0);
        if (partCost > 0) stats.incrementCostUsd(partCost);

        JSONObject tokens = part.optJSONObject("tokens");
        if (tokens == null) return;

        stats.incrementInputTokens(tokens.optLong("input", 0));
        stats.incrementOutputTokens(tokens.optLong("output", 0));
        stats.incrementReasoningTokens(tokens.optLong("reasoning", 0));
        long partTotal = tokens.optLong("total", 0);
        if (partTotal > 0) stats.incrementTotalTokens(partTotal);

        JSONObject cache = tokens.optJSONObject("cache");
        if (cache != null) {
            stats.incrementCacheReadTokens(cache.optLong("read", 0));
            stats.incrementCacheWriteTokens(cache.optLong("write", 0));
        }
    }
}
