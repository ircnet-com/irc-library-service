package com.ircnet.library.service.parser;

public class ParserMapping {
    private String key;
    private int index;
    private int argumentCount;
    private ParserMethod parserMethod;

    public ParserMapping(String key, int index, int argumentCount, ParserMethod parserMethod) {
        this.key = key;
        this.index = index;
        this.argumentCount = argumentCount;
        this.parserMethod = parserMethod;
    }

    public String getKey() {
        return key;
    }

    public int getIndex() {
        return index;
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public ParserMethod getParserMethod() {
        return parserMethod;
    }
}
