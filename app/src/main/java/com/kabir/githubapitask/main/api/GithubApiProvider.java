package com.kabir.githubapitask.main.api;

import com.android.volley.Request;
import com.kabir.githubapitask.main.model.CommitItem;
import com.kabir.githubapitask.util.api.ApiParameter;
import com.kabir.githubapitask.util.api.provider.ApiProvider;
import com.kabir.githubapitask.util.api.provider.VolleyApiProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Kabir on 27-06-2016.
 */
public class GithubApiProvider implements GithubApi {

    private ApiProvider apiProvider;

    public GithubApiProvider() {
        apiProvider = new VolleyApiProvider();
    }

    @Override
    public Observable<List<CommitItem>> getCommits(int page, int perPage) {

        Map<String, String> params = new HashMap<>();
        params.put(ApiParameter.PAGE, String.valueOf(page));
        params.put(ApiParameter.PER_PAGE, String.valueOf(perPage));

        String url = "https://api.github.com/repos/rails/rails/commits";
        return apiProvider.callApi(Request.Method.GET, url, params, GET_COMMITS_API_TAG)
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .map(new Func1<String, JSONArray>() {
                              @Override
                              public JSONArray call(String response) {

                                  try {
                                      return new JSONArray(response);
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                      return null;
                                  }
                              }
                          })
                          .flatMap(new Func1<JSONArray, Observable<List<CommitItem>>>() {
                              @Override
                              public Observable<List<CommitItem>> call(JSONArray jsonArray) {

                                  if (jsonArray == null || jsonArray.length() == 0) {
                                      return Observable.just(null);
                                  }

                                  List<CommitItem> list = new ArrayList<>();
                                  for (int i = 0; i < jsonArray.length(); i++) {

                                      try {

                                          JSONObject jsonObject = jsonArray.getJSONObject(i);
                                          CommitItem commitItem = CommitItem.parseJSON(jsonObject);
                                          list.add(commitItem);
                                      } catch (JSONException e) {
                                          e.printStackTrace();
                                      }
                                  }

                                  return Observable.just(list);
                              }
                          });
    }

    @Override
    public void cancelGetCommitsCalls() {
        apiProvider.cancelApiCalls(GET_COMMITS_API_TAG);
    }
}
