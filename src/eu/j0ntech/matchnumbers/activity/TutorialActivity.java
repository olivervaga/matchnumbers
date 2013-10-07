package eu.j0ntech.matchnumbers.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import eu.j0ntech.matchnumbers.R;
import eu.j0ntech.matchnumbers.fragment.TutorialFragment;

public class TutorialActivity extends FragmentActivity {

	private static final int NUM_PAGES = 5;

	private ViewPager mPager;

	private PagerAdapter mPagerAdapter;

	private ArrayList<TutorialFragment> mFragmentList;

	private Button mNextButton;

	private Button mPrevButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tutorial);

		mPager = (ViewPager) findViewById(R.id.pager);

		mPagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager(), this);

		mPager.setAdapter(mPagerAdapter);

		mNextButton = (Button) findViewById(R.id.button_next);
		mPrevButton = (Button) findViewById(R.id.button_previous);

		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentItem = mPager.getCurrentItem();
				if (currentItem == NUM_PAGES - 2)
					mNextButton.setVisibility(View.INVISIBLE);
				else {
					mNextButton.setVisibility(View.VISIBLE);
					mPrevButton.setVisibility(View.VISIBLE);
				}
				if (currentItem < NUM_PAGES - 1)
					mPager.setCurrentItem(currentItem + 1);
			}
		});

		mPrevButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentItem = mPager.getCurrentItem();
				if (currentItem == 1)
					mPrevButton.setVisibility(View.INVISIBLE);
				else {
					mPrevButton.setVisibility(View.VISIBLE);
					mNextButton.setVisibility(View.VISIBLE);
				}
				if (currentItem > 0)
					mPager.setCurrentItem(currentItem - 1);

			}
		});
	}

	private class TutorialPagerAdapter extends FragmentPagerAdapter {

		TutorialActivity mParent;

		public TutorialPagerAdapter(FragmentManager fm, TutorialActivity parent) {
			super(fm);
			mParent = parent;
			initFragments();
		}

		private void initFragments() {
			mFragmentList = new ArrayList<TutorialFragment>();
			TutorialFragment page1 = new TutorialFragment();
			page1.setImageDrawable(mParent.getResources().getDrawable(R.drawable.page1));
			TutorialFragment page2 = new TutorialFragment();
			page2.setImageDrawable(mParent.getResources().getDrawable(R.drawable.page2));
			TutorialFragment page3 = new TutorialFragment();
			page3.setImageDrawable(mParent.getResources().getDrawable(R.drawable.page3));
			TutorialFragment page4 = new TutorialFragment();
			page4.setImageDrawable(mParent.getResources().getDrawable(R.drawable.page4));
			TutorialFragment page5 = new TutorialFragment();
			page5.setImageDrawable(mParent.getResources().getDrawable(R.drawable.page5));
			mFragmentList.add(page1);
			mFragmentList.add(page2);
			mFragmentList.add(page3);
			mFragmentList.add(page4);
			mFragmentList.add(page5);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

	}

}
