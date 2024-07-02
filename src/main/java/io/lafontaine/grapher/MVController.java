package io.lafontaine.grapher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class MVController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/graph")
    public String graph(@RequestParam(name="graph", required=false) String graphID, @RequestParam(name="from", required=false) String from, @RequestParam(name="to", required=false) String to, Model model) {
        String json;

        // Get graph from db using graphID

        Graph graph = new Graph();
        Graph.fillGraphBIG(graph);

        if ((from != null && to != null) && graph.exists(from) && graph.exists(to)){
            var solution = Solver.shortestPath(graph, graph.getNodes().get(from), graph.getNodes().get(to));
            var distance = Solver.shortestPathLength(solution);
            model.addAttribute("distance", distance);
            json = graph.Graph2Cy(graph, solution);
        } else {
            json = graph.Graph2Cy(graph, null);
        }
        model.addAttribute("json", json);
        model.addAttribute("nodes", graph.getNodes().values());
        return "graph";
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        //Upload.uploadFile(file);
        return "redirect:/graph";
    }
}