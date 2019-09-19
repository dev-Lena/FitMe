package com.example.fitme;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class image_searching_trial {
//    edittext_image_searching
//            button_image_searching
//    textView_image_searching
    EditText edittext_image_searching;
    Button button_image_searching;
    TextView textView_image_searching;
    ProgressBar progressBar;
        public static void main(String[] args) throws Exception {
            /**안씀**/

//            Connection.Response res = Jsoup.connect(
////                    String urlString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + key + "&cx=" + cx + "&alt=json";
//
//            "https://www.googleapis.com/customsearch/v1?key=AIzaSyBfiCE4HQwjpXD7wwQOWb73L1Z-yIKht7w&cx=002903084579047783547:njrybpitwx0&q=커피");
//                    .ignoreContentType(true).execute();
//            Document doc = (Document) res.parse();
//            JsonParser par = new JsonParser();
//            JSONObject jo_body = (JSONObject) par.parse(doc.selectFirst("body").text());
//            JSONArray ja_items = (JSONArray) jo_body.get("items"); // "items":[....]
//            if (ja_items==null) {
//                return;
//            } else {
//                for (int i = 0; i < ja_items.length(); i++) {  //원래 size 였음
//                    JSONObject jo_items = (JSONObject) ja_items.get(i);
//                    JSONObject jo_pgmap = (JSONObject) jo_items.get("pagemap");
//                    JSONArray ja_imageUrl = (JSONArray) jo_pgmap.get("cse_image");
//                    if (ja_imageUrl == null) {
//                        continue;
//                    }
//                    JSONObject jo_srcUrl = (JSONObject) ja_imageUrl.get(0);
//                    // String srcImageUrl = (String) jo_srcUrl.get("src");
//                    String srcImageUrl = jo_srcUrl.get("src").toString();
//                    System.out.println("******************다운로드**************");
//                    Response resultImageResponse;
//                    // Open a URL Stream
//                    try {
//                        resultImageResponse = Jsoup.connect(srcImageUrl).ignoreContentType(true).execute();
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                        continue;
//                    }
//                    // output here
//                    String[] t = srcImageUrl.split("/");
//                    String filename = t[t.length - 1];
//                    if (!filename.toUpperCase().endsWith(".JPG") && !filename.toUpperCase().endsWith(".PNG")
//                            && !filename.toUpperCase().endsWith(".GIF")) {
//                        filename = new Date().getTime() + ".jpg";
//                    }
//
//                    FileOutputStream out = (new FileOutputStream(filename));
//                    out.write(resultImageResponse.bodyAsBytes()); // resultImageResponse.body() is where the image's
//                    // contents are.
//                    out.close();
//                    System.out.println(srcImageUrl);
//                }
//            }
        }
    }