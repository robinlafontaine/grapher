package io.lafontaine.grapher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Float.isNaN;

@Controller
public class MVController {

    private static final String UPLOAD_DIRECTORY = "uploads";

    @Autowired
    private GraphRepository graphRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/drop")
    public String drop(Model model) {
        graphRepository.deleteAll();
        return "redirect:/graph";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Graph graph = new Graph();
        Graph.fillGraphBIG(graph);
        String json = Graph.toJson(graph);
        saveGraph(json, json.hashCode());
        return "redirect:/graph";
    }

    @GetMapping("/graph")
    public String graph(@RequestParam(name="id", required=false) String id, @RequestParam(name="from", required=false) String from, @RequestParam(name="to", required=false) String to, Model model) {
        String json;
        Graph graph;
        List<GraphDB> all_graphs = graphRepository.findAll();
        GraphDB graph_db = null;
        try {
            graph_db = graphRepository.findById(Long.parseLong(id)).orElse(null);
            model.addAttribute("id", Integer.parseInt(id));
        } catch (Exception e) {
            System.out.println("Error getting graph");
        }

        if (graph_db != null) {
            json = graph_db.getJson();
            graph = Graph.fromJson(json);
        } else {
            graph = new Graph();
            Graph.fillGraphBIG(graph);
        }

        if ((from != null && to != null) && graph.exists(from) && graph.exists(to)){
            var solution = Solver.shortestPath(graph, graph.getNodes().get(from), graph.getNodes().get(to));
            var distance = Solver.shortestPathLength(solution);
            model.addAttribute("distance", distance);
            json = graph.Graph2Cy(graph, solution);
        } else {
            json = graph.Graph2Cy(graph, null);
        }
        model.addAttribute("json", json);
        model.addAttribute("graphs", all_graphs);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("nodes", graph.getNodes().values());
        return "graph";
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("file") MultipartFile file) {
        try {
            uploadFile(file);
        } catch (Exception e) {
            System.out.println("Error uploading file");
        }
        return "redirect:/graph";
    }

    public boolean hashcodeExists(int hashcode) {
        return graphRepository.findByhashcode(hashcode) != null;
    }

    public void saveGraph(String json, int hashcode) {
        GraphDB graph = new GraphDB();
        graph.setJson(json);
        graph.setHashcode(hashcode);
        graphRepository.save(graph);
    }

    public void uploadFile(MultipartFile file) {
        String json_content;
        String filename = file.getOriginalFilename();
        int hashcode;
        try {
            Upload.validateFileSafety(file);
            Upload.tmpSaveFile(file, UPLOAD_DIRECTORY);
            json_content = Files.readString(Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename()));
            hashcode = json_content.hashCode();
            if (Upload.isValidJson(json_content) && !hashcodeExists(hashcode)) {
                saveGraph(json_content, hashcode);
            } else {
                System.out.println("Invalid JSON file or Graph already exists");
            }
            Path filePath = Paths.get(UPLOAD_DIRECTORY, filename);
            Files.delete(filePath);
        } catch (Exception e) {
            System.out.println("Error uploading file");
        }
    }
}