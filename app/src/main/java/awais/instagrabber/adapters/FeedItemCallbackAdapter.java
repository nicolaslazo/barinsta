package awais.instagrabber.adapters;

import android.view.View;

import awais.instagrabber.models.FeedModel;

public class FeedItemCallbackAdapter implements FeedAdapterV2.FeedItemCallback {
    @Override
    public void onPostClick(final FeedModel feedModel, final View profilePicView, final View mainPostImage) {}

    @Override
    public void onProfilePicClick(final FeedModel feedModel, final View profilePicView) {}

    @Override
    public void onNameClick(final FeedModel feedModel, final View profilePicView) {}

    @Override
    public void onLocationClick(final FeedModel feedModel) {}

    @Override
    public void onMentionClick(final String mention) {}

    @Override
    public void onHashtagClick(final String hashtag) {}

    @Override
    public void onCommentsClick(final FeedModel feedModel) {}

    @Override
    public void onDownloadClick(final FeedModel feedModel) {}

    @Override
    public void onEmailClick(final String emailId) {}

    @Override
    public void onURLClick(final String url) {}

    @Override
    public void onSliderClick(final FeedModel feedModel, final int position) {}
}
