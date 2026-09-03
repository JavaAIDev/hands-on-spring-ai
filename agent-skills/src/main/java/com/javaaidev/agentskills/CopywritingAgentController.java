package com.javaaidev.agentskills;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/copywriting")
public class CopywritingAgentController {

  private final CopywritingAgentService copywritingAgentService;

  public CopywritingAgentController(
      CopywritingAgentService copywritingAgentService) {
    this.copywritingAgentService = copywritingAgentService;
  }

  @PostMapping("/tasks")
  public CopywritingTaskResponse run(
      @Valid @RequestBody CopywritingTaskRequest request) {
    return copywritingAgentService.create(request);
  }
}
