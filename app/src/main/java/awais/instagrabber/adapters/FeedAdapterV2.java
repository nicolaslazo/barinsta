package awais.instagrabber.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import awais.instagrabber.adapters.viewholder.FeedGridItemViewHolder;
import awais.instagrabber.adapters.viewholder.feed.FeedItemViewHolder;
import awais.instagrabber.adapters.viewholder.feed.FeedPhotoViewHolder;
import awais.instagrabber.adapters.viewholder.feed.FeedSliderViewHolder;
import awais.instagrabber.adapters.viewholder.feed.FeedVideoViewHolder;
import awais.instagrabber.databinding.ItemFeedGridBinding;
import awais.instagrabber.databinding.ItemFeedPhotoBinding;
import awais.instagrabber.databinding.ItemFeedSliderBinding;
import awais.instagrabber.databinding.ItemFeedVideoBinding;
import awais.instagrabber.models.FeedModel;
import awais.instagrabber.models.PostsLayoutPreferences;
import awais.instagrabber.models.enums.MediaItemType;

public final class FeedAdapterV2 extends ListAdapter<FeedModel, RecyclerView.ViewHolder> {
    private static final String TAG = "FeedAdapterV2";

    private PostsLayoutPreferences layoutPreferences;
    private final FeedItemCallback feedItemCallback;

    // private final View.OnLongClickListener longClickListener = v -> {
    //     final Object tag;
    //     if (v instanceof RamboTextView && (tag = v.getTag()) instanceof FeedModel)
    //         Utils.copyText(v.getContext(), ((FeedModel) tag).getPostCaption());
    //     return true;
    // };


    private static final DiffUtil.ItemCallback<FeedModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<FeedModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull final FeedModel oldItem, @NonNull final FeedModel newItem) {
            return oldItem.getPostId().equals(newItem.getPostId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull final FeedModel oldItem, @NonNull final FeedModel newItem) {
            return oldItem.getPostId().equals(newItem.getPostId());
        }
    };

    public FeedAdapterV2(@NonNull final PostsLayoutPreferences layoutPreferences,
                         final FeedItemCallback feedItemCallback) {
        super(DIFF_CALLBACK);
        this.layoutPreferences = layoutPreferences;
        this.feedItemCallback = feedItemCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (layoutPreferences.getType()) {
            case LINEAR:
                return getLinearViewHolder(parent, layoutInflater, viewType);
            case GRID:
            case STAGGERED_GRID:
            default:
                final ItemFeedGridBinding binding = ItemFeedGridBinding.inflate(layoutInflater, parent, false);
                return new FeedGridItemViewHolder(binding);
        }
    }

    @NonNull
    private RecyclerView.ViewHolder getLinearViewHolder(@NonNull final ViewGroup parent,
                                                        final LayoutInflater layoutInflater,
                                                        final int viewType) {
        switch (MediaItemType.valueOf(viewType)) {
            case MEDIA_TYPE_VIDEO: {
                final ItemFeedVideoBinding binding = ItemFeedVideoBinding.inflate(layoutInflater, parent, false);
                return new FeedVideoViewHolder(binding, feedItemCallback);
            }
            case MEDIA_TYPE_SLIDER: {
                final ItemFeedSliderBinding binding = ItemFeedSliderBinding.inflate(layoutInflater, parent, false);
                return new FeedSliderViewHolder(binding, feedItemCallback);
            }
            case MEDIA_TYPE_IMAGE:
            default: {
                final ItemFeedPhotoBinding binding = ItemFeedPhotoBinding.inflate(layoutInflater, parent, false);
                return new FeedPhotoViewHolder(binding, feedItemCallback);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        final FeedModel feedModel = getItem(position);
        if (feedModel == null) return;
        feedModel.setPosition(position);
        switch (layoutPreferences.getType()) {
            case LINEAR:
                ((FeedItemViewHolder) viewHolder).bind(feedModel);
                break;
            case GRID:
            case STAGGERED_GRID:
            default:
                ((FeedGridItemViewHolder) viewHolder).bind(feedModel, layoutPreferences, feedItemCallback);
        }
    }

    @Override
    public int getItemViewType(final int position) {
        return getItem(position).getItemType().getId();
    }

    public void setLayoutPreferences(@NonNull final PostsLayoutPreferences layoutPreferences) {
        this.layoutPreferences = layoutPreferences;
    }

    // @Override
    // public void onViewAttachedToWindow(@NonNull final FeedItemViewHolder holder) {
    //     super.onViewAttachedToWindow(holder);
    //     // Log.d(TAG, "attached holder: " + holder);
    //     if (!(holder instanceof FeedSliderViewHolder)) return;
    //     final FeedSliderViewHolder feedSliderViewHolder = (FeedSliderViewHolder) holder;
    //     feedSliderViewHolder.startPlayingVideo();
    // }
    //
    // @Override
    // public void onViewDetachedFromWindow(@NonNull final FeedItemViewHolder holder) {
    //     super.onViewDetachedFromWindow(holder);
    //     // Log.d(TAG, "detached holder: " + holder);
    //     if (!(holder instanceof FeedSliderViewHolder)) return;
    //     final FeedSliderViewHolder feedSliderViewHolder = (FeedSliderViewHolder) holder;
    //     feedSliderViewHolder.stopPlayingVideo();
    // }

    public interface FeedItemCallback {
        void onPostClick(final FeedModel feedModel,
                         final View profilePicView,
                         final View mainPostImage);

        void onProfilePicClick(final FeedModel feedModel,
                               final View profilePicView);

        void onNameClick(final FeedModel feedModel,
                         final View profilePicView);

        void onLocationClick(final FeedModel feedModel);

        void onMentionClick(final String mention);

        void onHashtagClick(final String hashtag);

        void onCommentsClick(final FeedModel feedModel);

        void onDownloadClick(final FeedModel feedModel);

        void onEmailClick(final String emailId);

        void onURLClick(final String url);

        void onSliderClick(FeedModel feedModel, int position);
    }
}