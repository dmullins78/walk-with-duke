package org.cijug.cukes;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.cijug.config.WebAppConfigurationAware;
import org.cijug.invoice.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class InvoiceStepDefs extends WebAppConfigurationAware {

    private String outstandingBalance;

    @Before
    public void setup() throws IOException {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Given("^the following invoices$")
    public void the_following_invoices(List<Invoice> invoices) throws Throwable {
        for (Invoice invoice : invoices) {
            mockMvc.perform(post("/invoice")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("name", invoice.getName())
                            .param("paid", "" + invoice.isPaid())
                            .param("amount", "" + invoice.getAmount())
            ).andExpect(view().name("redirect:/invoices"));
        }
    }

    @When("^I see my outstanding balance$")
    public void i_see_my_outstanding_balance() throws Throwable {
        MvcResult mvcResult = mockMvc.perform(get("/invoices"))
                .andExpect(model().attributeExists("invoices"))
                .andExpect(view().name("invoices"))
                .andExpect(content().string(allOf(containsString("Google"), containsString("Apple"))))
                .andReturn();

        outstandingBalance = (String) mvcResult.getModelAndView().getModel().get("balance");
    }

    @Then("^I should see \"(.*?)\"$")
    public void i_should_see(String expected) throws Throwable {
        assertThat(outstandingBalance, is(expected));
    }

}
