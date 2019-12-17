package webservice;

import data.AddressBusiness;
import data.OperatorBusiness;
import data.PhoneBookBusiness;
import data.TelephoneContactBusiness;
import ejb.BusinessBeanInterface;

import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.enterprise.context.RequestScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/phonebook")
@RequestScoped //An object which is defined as @RequestScoped is
// created once for every request and is shared by all the bean that inject it throughout a request.
//https://javaee.github.io/javaee-spec/javadocs/javax/enterprise/context/RequestScoped.html
public class PhoneBookREST {
    //https://restfulapi.net/rest-api-design-tutorial-with-example/ - how to create resources...
    //https://hackernoon.com/restful-api-design-step-by-step-guide-2f2c9f9fcdbf 
    @EJB
    private BusinessBeanInterface ejb;
    SessionContext ctx;

    public PhoneBookREST(){
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            System.out.println("CANNOT CREATE INITIALCONTEXT");
            //e.printStackTrace();
        }
        try {
            ejb = (BusinessBeanInterface) ctx.lookup("global/EAR/org.example-PhoneBookBusiness-1.0-SNAPSHOT/BusinessBean!ejb.BusinessBeanInterface");
        } catch (NamingException e) {
            System.out.println("CANNOT GET THE BEAN INTERFACE FROM BUSINESS LAYER");
            //e.printStackTrace();
        }
    }

    @GET
    @Path("/contacts")
    @Produces({MediaType.APPLICATION_XML})
    //@Produces({MediaType.APPLICATION_JSON})
    //@Produces({MediaType.TEXT_PLAIN})
    public Response getContacts(){//PhoneBookBusiness
        return Response.status(Response.Status.ACCEPTED).entity(ejb.getContacts()).build();
    }
    @GET
    @Path("/contacts/{id}")
    @Produces({MediaType.TEXT_XML})
    public Response getContact(@PathParam("id") int id){
        //System.out.println("ID = "+id);
        PhoneBookBusiness phonebook = ejb.getContact(id);
        if (phonebook != null)
            return Response.status(Response.Status.ACCEPTED).entity( phonebook ).build();
        else
            return Response.status(Response.Status.NOT_FOUND).entity( "The contact do not exist" ).build();
    }


    @DELETE
    @Path("/contacts/{id}") // delete a contact and all information of him
    @Produces({MediaType.TEXT_PLAIN})
    public Response removeContact(@PathParam("id") int id){
        boolean status = ejb.removeContact(id);

        if (status == true)
            return Response.status(Response.Status.ACCEPTED).entity("Contact removed").build();
        return Response.status(Response.Status.NOT_FOUND).entity("Contact do not exist").build();
    }

    //################# TO UPDATE ACONTACT ##############################################
    @PUT //
    @Path("/contacts/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContact(@PathParam("id") int id, TelephoneContactBusiness usr){
        //JSON: {"name":"username","dayBirth":"2000-01-01T00:00:00Z}"
        int status = ejb.updateContact(id, usr);
        if (status == 0)
            return Response.status(Response.Status.NO_CONTENT).entity("Contact updated").build();
        return Response.status(Response.Status.NOT_FOUND).entity("Contact do not exist").build();
    }

    @PUT
    @Path("/contacts/{id}/address") //to updates the contact's address
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContactAddress(@PathParam("id") int id, AddressBusiness adr){
        //JSON: {
        //	"houseNumber":"54",
        //	"street":"Street XYZ",
        //	"streetNumber":"42",
        //	"zipCode":"9220-225",
        //	"city":"LISBOA"
        //}
        int status = ejb.updateContactAddress(id, adr);
        if (status == 0)
            return Response.status(Response.Status.NO_CONTENT).entity("Address updated").build();
        else if (status == 1)
            return Response.status(Response.Status.NOT_FOUND).entity("Contact do not exist").build();
        else
            return Response.status(Response.Status.NOT_FOUND).entity("Address do not exist").build();
    }

    @PUT
    @Path("/contacts/{id}/{operatorID}") // to update the contact's operator: JSON: {"operatorName":"CHANGED-ZON","number":"93232323"}
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContactOperator(@PathParam("id") int id, @PathParam("operatorID") int opID, OperatorBusiness op){
        int status = ejb.updateContactOperator(id, opID, op);
        if (status == 0)
            return Response.status(Response.Status.NO_CONTENT).entity("Operator number updated").build();
        else if (status == 1)
            return Response.status(Response.Status.NOT_FOUND).entity("Contact do not exist").build();
        else
            return Response.status(Response.Status.NOT_FOUND).entity("Operator number do not exist").build();
    }

    //################# TO ADD AN ACONTACT ##############################################
    @POST //
    @Path("/contacts")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addContact(TelephoneContactBusiness contact){
        //JSON: {
        //	"name":"FILIPUS",
        //	"dayBirth":"1990-01-01T00:00:00Z"
        //}
        int status = ejb.addContact(contact);
        return Response.status(Response.Status.CREATED).entity("Contact created").build();
    }
    @POST
    @Path("/contacts/{id}/address")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addContactAddress(@PathParam("id") int id, AddressBusiness adr){
        //JSON: {
        //	"houseNumber":"50",
        //	"street":"Street aaa",
        //	"streetNumber":"40",
        //	"zipCode":"9220-215",
        //	"city":"LISBOA"
        //}
        int status = ejb.addContactAddress(id, adr);
        if (status == 0)
            return Response.status(Response.Status.CREATED).entity("Address created").build();
        else if (status == 1){
            return Response.status(Response.Status.NO_CONTENT).entity("Contact cannot have more than one address").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("Contact do not exist").build();
        }

    }
    @POST
    @Path("/contacts/{id}/number")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addContactOperator(@PathParam("id") int id, OperatorBusiness op){
        int status = ejb.addContactOperator(id, op);
        if (status == 0)
            return Response.status(Response.Status.CREATED).entity("Operator created").build();
        return Response.status(Response.Status.NOT_FOUND).entity("Contact do not exist").build();
    }

    @GET
    @Path("/deleteCall")//for testing - see if a simple message pass between all layers
    //@Produces({MediaType.TEXT_XML})
    //@Produces({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public String deleteCall(@DefaultValue("2") @QueryParam("step") int step,
                            @DefaultValue("true") @QueryParam("max-m") boolean hasMin,
                            @DefaultValue("Blue") @QueryParam("color") String color)
    {
        System.out.println("DELETED CALL STEP: "+step+" HASMIN = "+hasMin+" COLOR: "+color);
        return "DELETED CALL_"+ ejb.createCall("_PING_")+"_REST: STEP: "+step+" HASMIN = "+hasMin+" COLOR: "+color;
    }

}
