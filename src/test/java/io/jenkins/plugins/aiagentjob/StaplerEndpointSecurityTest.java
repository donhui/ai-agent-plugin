package io.jenkins.plugins.aiagentjob;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kohsuke.stapler.StaplerRequest2;
import org.kohsuke.stapler.StaplerResponse2;
import org.kohsuke.stapler.interceptor.RequirePOST;
import org.kohsuke.stapler.verb.GET;
import org.kohsuke.stapler.verb.POST;

public class StaplerEndpointSecurityTest {
    @Test
    public void descriptorEndpoints_usePostVerbs() throws Exception {
        assertTrue(
                AiAgentProject.DescriptorImpl.class
                        .getMethod(
                                "doFillApiCredentialsIdItems",
                                hudson.model.Item.class,
                                String.class)
                        .isAnnotationPresent(POST.class));
        assertTrue(
                AiAgentProject.DescriptorImpl.class
                        .getMethod(
                                "doCheckApprovalTimeoutSeconds",
                                hudson.model.Item.class,
                                String.class)
                        .isAnnotationPresent(POST.class));
    }

    @Test
    public void runActionEndpoints_useExpectedHttpVerbs() throws Exception {
        assertTrue(
                AiAgentRunAction.class
                        .getMethod(
                                "doProgressiveEvents",
                                StaplerRequest2.class,
                                StaplerResponse2.class)
                        .isAnnotationPresent(GET.class));
        assertTrue(
                AiAgentRunAction.class
                        .getMethod("doRaw", StaplerRequest2.class, StaplerResponse2.class)
                        .isAnnotationPresent(GET.class));
        assertTrue(
                AiAgentRunAction.class
                        .getMethod("doApprove", String.class)
                        .isAnnotationPresent(RequirePOST.class));
        assertTrue(
                AiAgentRunAction.class
                        .getMethod("doDeny", String.class, String.class)
                        .isAnnotationPresent(RequirePOST.class));
    }
}
