import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylist.all());
      model.put("template", "templates/stylists.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String details = request.queryParams("details");
      Stylist newStylist = new Stylist(name, details);
      newStylist.save();
      String url = "/stylists";
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist currentStylist = Stylist.find(Integer.parseInt(request.params("stylist_id")));
      model.put("stylist", currentStylist);
      model.put("clients", currentStylist.getClients());
      model.put("template", "/templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addnew", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String clientName = request.queryParams("clientName");
      String clientDetails = request.queryParams("clientDetails");
      int stylistId = Integer.parseInt(request.queryParams("stylistId"));
      Client newClient = new Client(clientName, stylistId, clientDetails);
      newClient.save();
      String url = String.format("stylists/%d", stylistId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
