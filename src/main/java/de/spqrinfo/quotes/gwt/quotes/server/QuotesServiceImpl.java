package de.spqrinfo.quotes.gwt.quotes.server;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import de.spqrinfo.quotes.gwt.quotes.shared.*;
import de.spqrinfo.quotes.util.SpringRemoteServiceServlet;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class QuotesServiceImpl extends SpringRemoteServiceServlet implements QuotesService {

    private List<Quotation> quotes;
    private Map<Integer, QuotationAuthor> authors;
    private Multimap<Integer, Quotation> quotesByAuthor;

    public QuotesServiceImpl() {
        final List<Quotation> result = Lists.newArrayList();

        final QuotationAuthor ludwigWittenstein = new QuotationAuthor(10, "Ludwig Wittgenstein");
        final Quotation lWittgenstein1 = new Quotation(1, "Die Welt ist alles, was der Fall ist.", 4,
                ludwigWittenstein, Lists.newArrayList(new QuotationTag("philosophy"),
                new QuotationTag("classical")));
        result.add(lWittgenstein1);

        final QuotationAuthor donaldKnuth = new QuotationAuthor(11, "Donald Knuth");
        final Quotation dKnuth1 = new Quotation(2, "Premature optimisation is the root of all evil.", 5,
                donaldKnuth, Lists.newArrayList(new QuotationTag("programming")));
        result.add(dKnuth1);

        final QuotationAuthor oscarWilde = new QuotationAuthor(3, "Oscar Wilde");

        final Quotation ow1 = new Quotation(3,
                "Der einzige Weg, eine Versuchung loszuwerden, ist, ihr nachzugeben.", 1,
                oscarWilde, Lists.newArrayList(new QuotationTag("belletristik")));
        result.add(ow1);

        final Quotation ow2 = new Quotation(4,
                "Ernsthaftigkeit ist die Zuflucht derer, die nichts zu sagen haben.", null,
                oscarWilde, Lists.newArrayList(new QuotationTag("tag1")));
        result.add(ow2);

        this.quotes = result;

        this.authors = Maps.newHashMap();
        this.authors.put(ludwigWittenstein.getId(), ludwigWittenstein);
        this.authors.put(donaldKnuth.getId(), donaldKnuth);
        this.authors.put(oscarWilde.getId(), oscarWilde);

        this.quotesByAuthor = ArrayListMultimap.create();
        this.quotesByAuthor.put(ludwigWittenstein.getId(), lWittgenstein1);
        this.quotesByAuthor.put(donaldKnuth.getId(), dKnuth1);
        this.quotesByAuthor.put(oscarWilde.getId(), ow1);
        this.quotesByAuthor.put(oscarWilde.getId(), ow2);
    }

    @Override
    public List<Quotation> getQuotes() {
        return Lists.newArrayList(this.quotes);
    }

    @Override
    public QuotationsOfAuthor getQuotesByAuthor(final Integer authorId) {
        final QuotationAuthor quotationAuthor = this.authors.get(authorId);
        if (quotationAuthor == null) {
            return null;
        }
        final Collection<Quotation> quotations = this.quotesByAuthor.get(authorId);
        return new QuotationsOfAuthor(quotationAuthor, Lists.newArrayList(quotations));
    }

    @Override
    public Quotation getQuote(final Integer quoteId) {
        for (final Quotation quote : this.quotes) {
            if (quote.getId().equals(quoteId)) {
                return quote;
            }
        }
        return null;
    }
}
