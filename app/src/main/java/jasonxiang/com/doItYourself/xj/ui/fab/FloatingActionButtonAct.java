package jasonxiang.com.doItYourself.xj.ui.fab;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jasonxiang.com.doItYourself.R;
import jasonxiang.com.doItYourself.xj.Model.Contact;
import jasonxiang.com.doItYourself.xj.base.BaseActivity;
import jasonxiang.com.doItYourself.xj.recycler.DividerItemDecoration;
import jp.wasabeef.recyclerview.animators.ScaleInRightAnimator;

/**
 * Created by xiangjian on 2016/11/11.
 */

//refer:https://github.com/codepath/android_guides/wiki/Floating-Action-Buttons
//https://github.com/codepath/android_guides/wiki/Using-the-RecyclerView
public class FloatingActionButtonAct extends BaseActivity {

    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_container)
    CoordinatorLayout parentView;
    @BindView(R.id.addBtn)
    Button addBtn;
    @BindView(R.id.scrollTopBtn)
    Button scrollTopBtn;
    @BindView(R.id.scrollBottomBtn)
    Button scrollBottomBtn;

    private List<Contact> mContacts;
    private ContactsAdaptor mAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fab;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContacts = Contact.createContactsList(5);
        // Create adapter passing in the sample user data
        mAdapter = new ContactsAdaptor(this, mContacts);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(mAdapter);
        // Set layout manager to position the items
//        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvContacts.setLayoutManager(gridLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvContacts.addItemDecoration(itemDecoration);
        rvContacts.setItemAnimator(new ScaleInRightAnimator());
        //TODO https://github.com/codepath/android_guides/wiki/Heterogenous-Layouts-inside-RecyclerView
    }

    @OnClick(R.id.fab)
    public void snackBar() {
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        Snackbar.make(parentView, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action, myOnClickListener)
                .setActionTextColor(ContextCompat.getColor(FloatingActionButtonAct.this, R.color.blue))
                .show(); // Don’t forget to show!
//        Snackbar.make(parentView, R.string.snackbar_text, Snackbar.LENGTH_INDEFINITE).show();
    }

    @OnClick(R.id.addBtn)
    public void addContacts() {
        int curSize = mAdapter.getItemCount();

        ArrayList<Contact> newItems = Contact.createContactsList(5);

        mContacts.addAll(newItems);
// curSize should represent the first element that got added
// newItems.size() represents the itemCount
        mAdapter.notifyItemRangeInserted(curSize, newItems.size());
    }

    @OnClick(R.id.scrollTopBtn)
    public void scrollTopContacts() {
        addContacts();
        rvContacts.scrollToPosition(0);
    }

    @OnClick(R.id.scrollBottomBtn)
    public void scrollBottomContacts() {
        addContacts();
        rvContacts.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(FloatingActionButtonAct.this, "嗯哼？", Toast.LENGTH_LONG).show();
        }
    }

    // Create the basic adapter extending from RecyclerView.Adapter
    // Note that we specify the custom ViewHolder which gives us access to our views
    public class ContactsAdaptor extends RecyclerView.Adapter<ContactsAdaptor.ViewHolder> {

        private List<Contact> mContacts;
        private Context mContext;

        public ContactsAdaptor(Context context, List<Contact> contacts) {
            this.mContext = context;
            this.mContacts = contacts;
        }

        public Context getContext() {
            return mContext;
        }

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.contact_name)
            TextView nameTextView;
            @BindView(R.id.message_button)
            Button messageButton;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
//                nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
//                messageButton = (Button) itemView.findViewById(R.id.message_button);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition(); // gets item position
                if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                    Contact contact = mContacts.get(position);
                    // We can access the data within the views
                    Toast.makeText(mContext, contact.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.nameTextView.setText(contact.getName());
            holder.messageButton.setText(Boolean.toString(contact.isOnline()));
        }

        @Override
        public int getItemCount() {
            return mContacts != null ? mContacts.size() : 0;
        }

//        public void swapItems(List<Contact> contacts) {
//            // compute diffs
//            final ContactDiffCallback diffCallback = new ContactDiffCallback(this.mContacts, contacts);
//            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
//
//            // clear contacts and add
//            this.mContacts.clear();
//            this.mContacts.addAll(contacts);
//
//            diffResult.dispatchUpdatesTo(this); // calls adapter's notify methods after diff is computed
//        }

    }

}
