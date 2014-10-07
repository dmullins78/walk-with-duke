package org.cijug.invoice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceControllerTest {

    Model model;
    @Mock InvoiceRepository invoiceRepository;
    @InjectMocks InvoiceController controller;
    private List<Invoice> invoices;

    @Before
    public void setUp() throws Exception {
        model = new ExtendedModelMap();

        Invoice notPaid = new Invoice("one", 200, false);
        Invoice paid = new Invoice("two", 100, true);
        invoices = asList(notPaid, paid);
    }

    @Test
    public void shouldPutInvoicesInMap() throws Exception {
        when(invoiceRepository.getAll()).thenReturn(invoices);

        controller.showOpenInvoices(model);

        Collection<Invoice> invoices = (Collection<Invoice>) fromMap("invoices");
        assertThat(2, is(invoices.size()));
    }

    @Test
    public void shouldCalculateOutstandingBalance() throws Exception {
        when(invoiceRepository.getAll()).thenReturn(invoices);

        controller.showOpenInvoices(model);

        assertThat("200", is(fromMap("balance")));
    }

    private Object fromMap(String value) {
        return model.asMap().get(value);
    }

}