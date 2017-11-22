package soberich.magicdate7.view.viewobjects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import org.joda.time.LocalDate;

import java.util.List;

import soberich.magicdate7.BR;

/**
 *
 * Created by soberich on 9/17/17.
 */

@Entity(tableName = "res_entries")
public class ResEntry extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("b_date")
    private LocalDate bDate;
    @SerializedName("result_date")
    private LocalDate resultDate;
    @SerializedName("comment")
    private String comment;
    @SerializedName("description")
    @Ignore
    private List<String> description;
    private boolean isAddedToCal;
    private boolean isExpanded;

/*    public ResEntry(int id,
                    @NonNull LocalDate bDate,
                    @NonNull LocalDate resultDate,
                    @NonNull String comment,
                    @Nullable List<String> description,
                    boolean isAddedToCal,
                    boolean isExpanded) {
        this.id = id;
        this.bDate = bDate;
        this.resultDate = resultDate;
        this.comment = comment;
        this.description = description;
        this.isAddedToCal = isAddedToCal;
        this.isExpanded = isExpanded;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public LocalDate getBDate() {
        return bDate;
    }

    public void setBDate(LocalDate bDate) {
        this.bDate = bDate;
        notifyPropertyChanged(BR.bDate);
    }
    @Bindable
    public LocalDate getResultDate() {
        return resultDate;
    }

    public void setResultDate(LocalDate resultDate) {
        this.resultDate = resultDate;
    }
    @Bindable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
    }
    @Bindable
    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }
    @Bindable
    public boolean isAddedToCal() {
        return isAddedToCal;
    }

    public void setAddedToCal(boolean addedToCal) {
        isAddedToCal = addedToCal;
    }
    @Bindable
    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
