package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

@Path("/users")
public class UserService {
    static ArrayList<User> users = new ArrayList<>();

    /**
     * A method which gets all Users that currently exist.
     * @return the list of all Users that currently exist.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        System.out.println("GET /users get");

        if (users.size() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\" : \"The list of users is currently empty!\"}").build();
        }

        return Response.ok(users).build();
    }

    /**
     * Receives the details of the entry and sends them for processing.
     * @param incomingData the details from which the User will be created.
     * @return the response from the creation (either successful or failed creation).
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(InputStream incomingData) {
        System.out.println("POST /users add");
        JsonNode userDetails = convertInputDataToJSON(incomingData);
        boolean doesUserExist = checkIfUserExists(userDetails);

        if (!doesUserExist) {
            User newUser = null;
            if (userDetails != null) {
                newUser = new User(userDetails);
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"Error\" : \"Unable to convert data to JSON\"}").build();
            }

            try {
                users.add(newUser);
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"Error\" : \"Unable to add User to the list!\"}").build();
            }
        } else {
            String userID = userDetails.get("userID").textValue();
            return Response.ok("{\"Status\" : \"User with ID " + userID + " already exists!\"}").build();
        }


        return Response.ok("{\"Status\" : \"New User successfully created!\"}").build();
    }

    /**
     * Deletes a User.
     * @param userID the ID of the User to be deleted.
     * @return the response from the operation (either successful or failed deletion).
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") String userID) {
        System.out.println("DELETE /users delete");

        for (User user : users) {
            if (user.getID().equals(userID)) {
                users.remove(user);
                break;
            }
        }

        return Response.ok("{\"Status\" : \"User successfully deleted!\"}").build();
    }

    /**
     * Edits the details of the selected User.
     * @param userID the ID of the User to be edited.
     * @return the response from the database (either successful or failed modification).
     */
    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editCandidate(@PathParam("id") String userID, InputStream incomingData) {
        System.out.println("POST /users edit");
        JsonNode userDetails = convertInputDataToJSON(incomingData);

        for (User user : users) {
            if (user.getID().equals(userID)) {
                if (userDetails != null) {
                    user.setEmail(userDetails.get("userEmail").textValue());
                    user.setGivenName(userDetails.get("userGivenName").textValue());
                    user.setFamilyName(userDetails.get("userFamilyName").textValue());
                }
            }
        }

        return Response.ok("{\"Status\" : \"User with ID " + userID + " was successfully modified!\"}").build();
    }

    /**
     * Searches for User(s) in the list by provided string. If the given or family name contains
     * the provided string, the user will be displayed on the front-end.
     * @return the list of User(s) found.
     */
    @GET
    @Path("{searchStr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCandidate(@PathParam("searchStr") String searchStr) {
        System.out.println("GET /users search");
        ArrayList<User> foundUsers = new ArrayList<>();

        for (User user : users) {
            if (user.getGivenName().toLowerCase().contains(searchStr) ||
                user.getFamilyName().toLowerCase().contains(searchStr)) {
                foundUsers.add(user);
            }
        }

        return Response.ok(foundUsers).build();
    }

    private JsonNode convertInputDataToJSON(InputStream incomingData) {
        StringBuilder userDetailsStr = new StringBuilder();
        JsonNode jsonNode = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line;
            while ((line = in.readLine()) != null) {
                userDetailsStr.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: -");
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonNode = mapper.readTree(userDetailsStr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonNode;
    }

    private boolean checkIfUserExists(JsonNode userDetails) {
        boolean check = false;
        String newUserID = userDetails.get("userID").textValue();

        for (User user : users) {
            if (user.getID().equals(newUserID)) {
                check = true;
                break;
            }
        }

        return check;
    }
}
