package com.learning.plugin.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class HtmlRendererUtil {

    public static String parser(String content){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);

        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

}
