package io.lafontaine.grapher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.OnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class MVController {

    private static final String UPLOAD_DIRECTORY = "uploads";

    @Autowired
    private GraphRepository graphRepository;

    @GetMapping("/")
    public String index(Model model) {
        Graph graph = new Graph();
        Graph big = new Graph();
        Graph.fillGraph(graph);
        Graph.fillGraphBIG(big);
        String json = Graph.toJson(graph);
        String json_big = Graph.toJson(big);
        saveGraph(json, json.hashCode(), "Default");
        saveGraph(json_big, json_big.hashCode(), "Big");
        return "redirect:/graph";
    }

    @GetMapping("/drop")
    public String drop(Model model) {
        graphRepository.deleteAll();
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
    public String upload(Model model, @RequestParam(name="name") String name ,@RequestParam("file") MultipartFile file) {

        if (file.isEmpty() || name.isEmpty()) {
            System.out.println("Please select a file and a name !");
            return "redirect:/graph";
        }

        try {
            uploadFile(file, name);
        } catch (Exception e) {
            System.out.println("Error uploading file");
        }
        return "redirect:/graph";
    }

    public boolean hashcodeExists(int hashcode) {
        return graphRepository.findByhashcode(hashcode) != null;
    }

    public void saveGraph(String json, int hashcode, String name) {
        if (hashcodeExists(hashcode)) {
            System.out.println("Graph already exists");
            return;
        }
        GraphDB graph = new GraphDB();
        graph.setJson(json);
        graph.setHashcode(hashcode);
        graph.setName(name);
        graphRepository.save(graph);
        System.out.println("Graph saved");
    }

    public void uploadFile(MultipartFile file, String name) {
        String json_content;
        String filename = file.getOriginalFilename();
        int hashcode;
        try {
            Upload.validateFileSafety(file);
            Upload.tmpSaveFile(file, UPLOAD_DIRECTORY);
            json_content = Files.readString(Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename()));
            hashcode = json_content.hashCode();
            if (Upload.isValidJson(json_content) && !hashcodeExists(hashcode)) {
                saveGraph(json_content, hashcode, name);
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