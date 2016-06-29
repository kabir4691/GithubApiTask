package com.kabir.githubapitask.main.model;

import com.kabir.githubapitask.util.StringUtils;
import com.kabir.githubapitask.util.api.ApiParameter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kabir on 27-06-2016.
 */
public class CommitItem {

    private String avatar;
    private String author;
    private String sha;
    private String message;

    private String shaDisplayText;

    private CommitItem() {
    }

    public static CommitItem parseJSON(JSONObject jsonObject) throws JSONException {

        CommitItem commitItem = new CommitItem();

        JSONObject authorObject = jsonObject.getJSONObject(ApiParameter.AUTHOR);
        commitItem.avatar = authorObject.getString(ApiParameter.AVATAR_URL);

        String shaString = jsonObject.getString(ApiParameter.SHA);
        if (!StringUtils.isNullOrEmpty(shaString)) {
            commitItem.sha = shaString;
            commitItem.shaDisplayText = "Commit: " + shaString;
        }

        JSONObject commitObject = jsonObject.getJSONObject(ApiParameter.COMMIT);
        commitItem.message = commitObject.getString(ApiParameter.MESSAGE);

        commitItem.author = commitObject.getJSONObject(ApiParameter.AUTHOR)
                                        .getString(ApiParameter.NAME);


        return commitItem;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAuthor() {
        return author;
    }

    public String getSha() {
        return sha;
    }

    public String getMessage() {
        return message;
    }

    public String getShaDisplayText() {
        return shaDisplayText;
    }
}
