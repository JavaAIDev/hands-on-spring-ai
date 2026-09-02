# Spring AI Agent Skills with OpenAI and SkillsJars

This project demonstrates how to give an OpenAI-backed Spring AI agent copywriting knowledge through a versioned SkillsJar dependency.

The example uses the following dependency:

```xml
<dependency>
  <groupId>com.skillsjars</groupId>
  <artifactId>coreyhaines31__marketingskills__copywriting</artifactId>
  <version>2026_03_14-9d4d29a</version>
</dependency>
```

SkillsJars packages Agent Skills as JAR files. The `marketingskills__copywriting` JAR contains its `SKILL.md` at `META-INF/skills`. `CopywritingAgentService` loads that classpath location with `SkillsTool` from Spring AI Agent Utils and registers it as a default tool callback on `ChatClient`.

When a request involves marketing copy, the OpenAI agent can load the `marketingskills__copywriting` skill and follow its workflow to create clear, audience-appropriate copy.

## Prerequisites

- Java 25 or newer
- An OpenAI API key
- A model that supports function calling, such as `gpt-5.6-luna`

Set the API key:

```bash
export OPENAI_API_KEY=your-api-key
```

## Run

```bash
mvn spring-boot:run
```

Ask the agent to create marketing copy:

```bash
curl -X POST http://localhost:8080/copywriting/tasks \
  -H 'Content-Type: application/json' \
  -d '{"task":"Use the copywriting skill to write a concise launch email for a new project-management app aimed at small design teams."}'
```

## How It Works

1. Maven puts the SkillsJar on the application classpath.
2. `agent.skills.paths=classpath:/META-INF/skills` selects the location inside the JAR.
3. `SkillsTool` discovers the bundled `copywriting` skill and exposes it to the `ChatClient`.
4. The OpenAI agent requests the skill when its description matches the copywriting task.
5. Spring AI returns the skill instructions to the agent, which uses them to create the requested copy.

## Safety

Agent Skills are instructions, not a security boundary. Review skills and restrict the tools available to the agent before enabling capabilities beyond this example.
