package io.jenkins.plugins.aiagentjob.cursor;

import io.jenkins.plugins.aiagentjob.AgentUsageStats;
import io.jenkins.plugins.aiagentjob.AiAgentStatsExtractor;

import net.sf.json.JSONObject;

import java.util.Locale;

/**
 * Stats extractor for Cursor Agent. Cursor reports usage in "result" events with the standard cost,
 * duration, and usage structures.
 */
public final class CursorStatsExtractor implements AiAgentStatsExtractor {

    public static final CursorStatsExtractor INSTANCE = new CursorStatsExtractor();

    private CursorStatsExtractor() {}

    @Override
    public boolean extract(JSONObject json, AgentUsageStats stats) {
        String type = json.optString("type", "").toLowerCase(Locale.ROOT);

        if ("result".equals(type)) {
            stats.extractResultStats(json);
            return true;
        }

        return false;
    }
}
