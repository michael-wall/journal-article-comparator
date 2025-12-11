package com.mw.comparator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

public class ArticleCreateDateDescComparator extends OrderByComparator<JournalArticle> {

    public static final String ORDER_BY_ASC = "JournalArticle.createDate ASC";
    public static final String ORDER_BY_DESC = "JournalArticle.createDate DESC";
    public static final String[] ORDER_BY_FIELDS = {"createDate"};

    private final boolean ascending;

    // ✅ default public constructor
    public ArticleCreateDateDescComparator() {
        this(false); // or false, depending on your desired default
    }

    // Optional constructor for flexibility
    public ArticleCreateDateDescComparator(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(JournalArticle a1, JournalArticle a2) {
        int value = DateUtil.compareTo(a1.getCreateDate(), a2.getCreateDate());
        return ascending ? value : -value;
    }

    @Override
    public String getOrderBy() {
        return ascending ? ORDER_BY_ASC : ORDER_BY_DESC;
    }

    @Override
    public String[] getOrderByFields() {
        return ORDER_BY_FIELDS;
    }

    @Override
    public boolean isAscending() {
        return ascending;
    }
}
