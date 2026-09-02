# Spring AI Agent Skills with OpenAI and SkillsJars

This project demonstrates how to give an OpenAI-backed Spring AI agent browser-automation knowledge through a versioned SkillsJar dependency.

The example uses the following dependency:

```xml
<dependency>
  <groupId>com.skillsjars</groupId>
  <artifactId>browser-use__browser-use__browser-use</artifactId>
  <version>2026_02_23-1d154e1</version>
</dependency>
```

SkillsJars packages Agent Skills as JAR files. The `browser-use` JAR contains its `SKILL.md` at `META-INF/skills`. `BrowserUseAgentService` loads that classpath location with `SkillsTool` from Spring AI Agent Utils and registers it as a default tool callback on `ChatClient`.

When a request involves browser navigation, data extraction, screenshots, or form filling, the OpenAI agent can load the `browser-use` skill and follow its workflow. The skill teaches the agent how to operate the `browser-use` CLI; install and configure that CLI separately before permitting the agent to carry out browser actions.

## Prerequisites

- Java 21 or newer
- An OpenAI API key
- A model that supports function calling, such as `gpt-5-mini`
- The `browser-use` CLI, if browser actions should be executed

Set the API key:

```bash
export OPENAI_API_KEY=your-api-key
```

## Run

```bash
mvn spring-boot:run
```

Ask the agent to plan a browser task:

```bash
curl -X POST http://localhost:8080/browser/tasks \
  -H 'Content-Type: application/json' \
  -d '{"task":"Use the browser-use skill to explain how to open https://example.com and extract its page title."}'
```

## How It Works

1. Maven puts the SkillsJar on the application classpath.
2. `agent.skills.paths=classpath:/META-INF/skills` selects the location inside the JAR.
3. `SkillsTool` discovers the bundled `browser-use` skill and exposes it to the `ChatClient`.
4. The OpenAI agent requests the skill when its description matches the browser task.
5. Spring AI returns the skill instructions to the agent, which uses them to plan or execute the task when compatible browser tools are available.

## Safety

Agent Skills are instructions, not a security boundary. Review skills and restrict the tools available to the agent. This sample instructs the agent not to submit forms, make purchases, or modify accounts without the user's explicit confirmation.
