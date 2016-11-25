package org.craftsmenlabs.stories.api.models.validatorentry;

import java.util.List;

public class BacklogItemList<T extends BacklogItem >  {
    private List<T> items;
    private boolean isActive;

    @java.beans.ConstructorProperties({"items", "isActive"})
    public BacklogItemList(List<T> items, boolean isActive) {
        this.items = items;
        this.isActive = isActive;
    }

    public BacklogItemList() {
    }

    public static <T extends BacklogItem> BacklogItemListBuilder<T> builder() {
        return new BacklogItemListBuilder<T>();
    }

    public List<T> getItems() {
        return this.items;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BacklogItemList)) return false;
        final BacklogItemList other = (BacklogItemList) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$items = this.getItems();
        final Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
        if (this.isActive() != other.isActive()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        result = result * PRIME + (this.isActive() ? 79 : 97);
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof BacklogItemList;
    }

    public String toString() {
        return "org.craftsmenlabs.stories.api.models.validatorentry.BacklogItemList(items=" + this.getItems() + ", isActive=" + this.isActive() + ")";
    }

    public static class BacklogItemListBuilder<T extends BacklogItem> {
        private List<T> items;
        private boolean isActive;

        BacklogItemListBuilder() {
        }

        public BacklogItemList.BacklogItemListBuilder<T> items(List<T> items) {
            this.items = items;
            return this;
        }

        public BacklogItemList.BacklogItemListBuilder<T> isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public BacklogItemList<T> build() {
            return new BacklogItemList<T>(items, isActive);
        }

        public String toString() {
            return "org.craftsmenlabs.stories.api.models.validatorentry.BacklogItemList.BacklogItemListBuilder(items=" + this.items + ", isActive=" + this.isActive + ")";
        }
    }
}
