package io.lafontaine.grapher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static io.lafontaine.grapher.Graph.Graph2Json;

@Controller
public class MVController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/graph")
    public String graph(Model model) {
        //model.getAttribute(un_graph);è

        return "graph";
    }

    @GetMapping("/json")
    public String json(Model model) {
        Graph graph = new Graph();
        Graph.fillGraph(graph);
        String json = Graph2Json(graph);
        model.addAttribute("json", json);
        System.out.printf(json);
        String cy = graph.Json2Cy(json);
        System.out.println(cy);
        return "json";
    }

}