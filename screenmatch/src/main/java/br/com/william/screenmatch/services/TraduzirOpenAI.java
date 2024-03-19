package br.com.william.screenmatch.services;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class TraduzirOpenAI {
    public static String traduzirTexto(String texto){
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Traduza o texto : " + texto)
                .model("gpt-3.5-turbo-instruct")
                .temperature(0.7)
                .maxTokens(1000)
                .build();
        return service.createCompletion(completionRequest).getChoices().get(0).getText();
    }
}
