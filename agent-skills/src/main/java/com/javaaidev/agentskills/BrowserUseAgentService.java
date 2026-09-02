package com.javaaidev.agentskills;

import java.util.List;
import org.springaicommunity.agent.tools.SkillsTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/** An OpenAI agent whose browser automation knowledge comes from a SkillsJar. */
@Service
public class BrowserUseAgentService {

  private final ChatClient chatClient;

  public BrowserUseAgentService(ChatClient.Builder chatClientBuilder,
      @Value("${agent.skills.paths}") List<Resource> skillPaths) {
    this.chatClient = chatClientBuilder
        .defaultSystem("""
            You are a browser automation assistant. When a task involves navigating a web page,
            extracting data, taking a screenshot, or filling a form, first load and follow the
            browser-use skill. Do not perform irreversible actions such as submitting forms,
            purchases, or account changes without explicit user confirmation.
            """)
        .defaultTools(SkillsTool.builder()
            .addSkillsResources(skillPaths)
            .build())
        .defaultOptions(OpenAiChatOptions.builder()
            .reasoningEffort("none")
        )
        .build();
  }

  public BrowserTaskResponse plan(BrowserTaskRequest request) {
    String response = chatClient.prompt()
        .user(request.task())
        .call()
        .content();
    return new BrowserTaskResponse(response);
  }
}
