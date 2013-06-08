package eu.j0ntech.matchnumbers.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import eu.j0ntech.matchnumbers.R;

public class TutorialFragment extends Fragment {

	private Drawable drawable;
	
	public void setImageDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ImageView rootView = (ImageView) inflater.inflate(
				R.layout.fragment_tutorial, container, false);
		
		rootView.setImageDrawable(drawable);
		
		return rootView;
	}

}
