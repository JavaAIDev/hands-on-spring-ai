package com.javaaidev.agentskills;

import java.util.List;
import org.springaicommunity.agent.tools.SkillsTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/** An OpenAI agent whose copywriting knowledge comes from a SkillsJar. */
@Service
public class CopywritingAgentService {

  private final ChatClient chatClient;

  public CopywritingAgentService(ChatClient.Builder chatClientBuilder,
      @Value("${agent.skills.paths}") List<Resource> skillPaths) {
    this.chatClient = chatClientBuilder
        .defaultSystem("""
            You are a marketing copywriting assistant.
            When a request requires marketing copy, first load and follow the copywriting skill.
            Tailor the copy to the stated audience, channel, and goal.
            Ask for the missing context when it is necessary to produce accurate copy.
            """)
        .defaultTools(SkillsTool.builder()
            .addSkillsResources(skillPaths)
            .build())
        .defaultAdvisors(new SimpleLoggerAdvisor())
        .defaultOptions(OpenAiChatOptions.builder()
            .reasoningEffort("none")
        )
        .build();
  }

  public CopywritingTaskResponse create(CopywritingTaskRequest request) {
    String response = chatClient.prompt()
        .user(request.task())
        .call()
        .content();
    return new CopywritingTaskResponse(response);
  }
}
