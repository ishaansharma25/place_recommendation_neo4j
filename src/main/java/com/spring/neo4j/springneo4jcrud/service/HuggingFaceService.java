package com.spring.neo4j.springneo4jcrud.service;

import ai.djl.Application;
import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import ai.djl.modality.Classifications;
import ai.djl.modality.nlp.Translation;
import ai.djl.modality.Classifier;
import ai.djl.inference.Predictor;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import org.springframework.stereotype.Service;

@Service
public class HuggingFaceService {

    private final Predictor<String, String> sentimentPredictor;

    public HuggingFaceService() throws ModelException {
        // Load the Hugging Face model
        Model model = ModelException.newInstance("huggingface/bert-base-uncased");
        Translator<String, String> translator = new HuggingFaceTranslator();
        this.sentimentPredictor = model.newPredictor(translator);
    }

    public String analyzeSentiment(String text) throws TranslateException {
        // Use the model to analyze sentiment
        return sentimentPredictor.predict(text);
    }
}
