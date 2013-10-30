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

@SuppressWarnings("FieldMayBeFinal")
public class QuotesServiceImpl extends SpringRemoteServiceServlet implements QuotesService {

    private List<Quotation> quotes;
    private Map<Integer, QuotationAuthor> authors;
    private Multimap<Integer, Quotation> quotesByAuthor;
    private Multimap<String, Quotation> quotesByTagName;

    public QuotesServiceImpl() {
        this.quotes = Lists.newArrayList();

        final QuotationAuthor ludwigWittenstein = new QuotationAuthor(10, "Ludwig Wittgenstein");
        final QuotationTag philosophy = new QuotationTag("philosophy");
        final QuotationTag classical = new QuotationTag("classical");
        final Quotation lWittgenstein1 = new Quotation(1, "Die Welt ist alles, was der Fall ist.", 4,
                ludwigWittenstein, Lists.newArrayList(philosophy,
                classical));
        this.quotes.add(lWittgenstein1);

        final QuotationAuthor donaldKnuth = new QuotationAuthor(11, "Donald Knuth");
        final QuotationTag programming = new QuotationTag("programming");
        final Quotation dKnuth1 = new Quotation(2, "Premature optimisation is the root of all evil.", 5,
                donaldKnuth, Lists.newArrayList(programming));
        this.quotes.add(dKnuth1);

        final QuotationAuthor oscarWilde = new QuotationAuthor(3, "Oscar Wilde");

        final QuotationTag belletristik = new QuotationTag("belletristik");
        final Quotation ow1 = new Quotation(3,
                "Der einzige Weg, eine Versuchung loszuwerden, ist, ihr nachzugeben.", 1,
                oscarWilde, Lists.newArrayList(belletristik));
        this.quotes.add(ow1);

        final Quotation ow2 = new Quotation(4,
                "Ernsthaftigkeit ist die Zuflucht derer, die nichts zu sagen haben.", null,
                oscarWilde, Lists.newArrayList(belletristik));
        this.quotes.add(ow2);

        final QuotationAuthor philipGreenspun = new QuotationAuthor(4, "Philip Greenspun");
        final Quotation phg1 = new Quotation(5, "Any sufficiently complicated C or Fortran program contains an ad hoc, informally-specified, bug-ridden, slow implementation of half of Common Lisp.",
                4, philipGreenspun, Lists.newArrayList(programming));
        this.quotes.add(phg1);

        this.authors = Maps.newHashMap();
        this.authors.put(ludwigWittenstein.getId(), ludwigWittenstein);
        this.authors.put(donaldKnuth.getId(), donaldKnuth);
        this.authors.put(oscarWilde.getId(), oscarWilde);
        this.authors.put(philipGreenspun.getId(), philipGreenspun);

        this.quotesByAuthor = ArrayListMultimap.create();
        this.quotesByAuthor.put(ludwigWittenstein.getId(), lWittgenstein1);
        this.quotesByAuthor.put(donaldKnuth.getId(), dKnuth1);
        this.quotesByAuthor.put(oscarWilde.getId(), ow1);
        this.quotesByAuthor.put(oscarWilde.getId(), ow2);
        this.quotesByAuthor.put(philipGreenspun.getId(), phg1);

        this.quotesByTagName = ArrayListMultimap.create();
        this.quotesByTagName.put(philosophy.getTagName(), lWittgenstein1);
        this.quotesByTagName.put(classical.getTagName(), lWittgenstein1);
        this.quotesByTagName.put(programming.getTagName(), dKnuth1);
        this.quotesByTagName.put(programming.getTagName(), phg1);
        this.quotesByTagName.put(belletristik.getTagName(), ow1);
        this.quotesByTagName.put(belletristik.getTagName(), ow2);
    }

    @Override
    public List<Quotation> getQuotes() {
        return Lists.newArrayList(this.quotes);
    }

    @Override
    public KeyedQuotationCollection<QuotationAuthor> getQuotesByAuthor(final Integer authorId) {
        final QuotationAuthor quotationAuthor = this.authors.get(authorId);
        if (quotationAuthor == null) {
            return null;
        }
        final Collection<Quotation> quotations = this.quotesByAuthor.get(authorId);
        return new KeyedQuotationCollection<QuotationAuthor>(quotationAuthor, Lists.newArrayList(quotations));
    }

    @Override
    public KeyedQuotationCollection<QuotationTag> getQuotesByTagName(final String tagName) {
        final Collection<Quotation> quotations = this.quotesByTagName.get(tagName);
        return new KeyedQuotationCollection<QuotationTag>(new QuotationTag(tagName), Lists.newArrayList(quotations));
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
