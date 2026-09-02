package com.javaaidev.agentskills;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/browser")
public class BrowserUseAgentController {

  private final BrowserUseAgentService browserUseAgentService;

  public BrowserUseAgentController(BrowserUseAgentService browserUseAgentService) {
    this.browserUseAgentService = browserUseAgentService;
  }

  @PostMapping("/tasks")
  public BrowserTaskResponse run(@Valid @RequestBody BrowserTaskRequest request) {
    return browserUseAgentService.plan(request);
  }
}
