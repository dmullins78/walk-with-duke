package org.cijug.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Collection;

@Controller
class InvoiceController {

    private InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @RequestMapping(value = "invoice")
    public String showForm(Model model) {
        model.addAttribute("model", new Invoice());

        return "invoice";
    }

    @RequestMapping(value = "invoices")
    public String showOpenInvoices(Model model) {
        Collection<Invoice> invoices = invoiceRepository.getAll();
        model.addAttribute("invoices", invoices);

        Integer sum = invoices.stream().filter(Invoice::isOutstanding).mapToInt(Invoice::getAmount).sum();
        model.addAttribute("balance", "" + sum);

        return "invoices";
    }

    @RequestMapping(value = "invoice", method = RequestMethod.POST)
    public String postInvoice(@Valid @ModelAttribute("model") Invoice invoice,  Errors errors) {
        if (errors.hasErrors()) {
            return "invoice";
        }

        Invoice saved = invoiceRepository.save(invoice);
        System.out.println("saved.getId() = " + saved.getId());

        return "redirect:/invoices";
    }

}
