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

    post("/stylists/add", (request, response) -> {
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

    get("/stylists/:stylist_id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist currentStylist = Stylist.find(Integer.parseInt(request.params("stylist_id")));
      model.put("stylist", currentStylist);
      model.put("clients", currentStylist.getClients());
      model.put("template", "/templates/stylist-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist currentStylist = Stylist.find(Integer.parseInt(request.params("stylist_id")));
      String stylistNewName = request.queryParams("stylistName");
      String stylistNewDetails = request.queryParams("stylistDetails");
      currentStylist.updateDetails(stylistNewDetails);
      currentStylist.updateName(stylistNewName);
      String url = String.format("/stylists/%d", currentStylist.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist currentStylist = Stylist.find(Integer.parseInt(request.params("stylist_id")));
      currentStylist.delete();
      String url = "/stylists";
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/clients/add", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String clientName = request.queryParams("clientName");
      String clientDetails = request.queryParams("clientDetails");
      int stylistId = Integer.parseInt(request.queryParams("stylistId"));
      Client newClient = new Client(clientName, stylistId, clientDetails);
      newClient.save();
      String url = String.format("/stylists/%d", stylistId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylists/:stylist_id/clients/:client_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Stylist currentStylist = Stylist.find(Integer.parseInt(request.params("stylist_id")));
      Client currentClient = Client.find(Integer.parseInt(request.params("client_id")));
      model.put("stylist", currentStylist);
      model.put("client", currentClient);
      model.put("template", "/templates/client.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/:client_id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client oldClient = Client.find(Integer.parseInt(request.params("client_id")));
      oldClient.delete();
      int stylistId = Integer.parseInt(request.params("stylist_id"));
      String url = String.format("/stylists/%d", stylistId);
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/stylists/:stylist_id/clients/:client_id/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Client currentClient = Client.find(Integer.parseInt(request.params("client_id")));
      int stylistId = Integer.parseInt(request.params("stylist_id"));
      String clientNewName = request.queryParams("clientName");
      String clientNewDetails = request.queryParams("clientDetails");
      currentClient.updateDetails(clientNewDetails);
      currentClient.updateName(clientNewName);
      String url = String.format("/stylists/%d/clients/%d", stylistId, currentClient.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
