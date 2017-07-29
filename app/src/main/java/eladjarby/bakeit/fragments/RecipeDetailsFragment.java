package eladjarby.bakeit.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import eladjarby.bakeit.Models.BaseInterface;
import eladjarby.bakeit.Models.Model;
import eladjarby.bakeit.Models.ModelFiles;
import eladjarby.bakeit.Models.Recipe.Recipe;
import eladjarby.bakeit.Models.Recipe.RecipeSql;
import eladjarby.bakeit.Models.User.User;
import eladjarby.bakeit.Models.User.UserFirebase;
import eladjarby.bakeit.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String recipeId;

    private OnFragmentInteractionListener mListener;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RecipeDetailsFragment newInstance(String recipeId) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        final Recipe recipe = Model.instance.getRecipe(recipeId);
        ((TextView)contentView.findViewById(R.id.details_title)).setText(recipe.getRecipeTitle());
        ((TextView)contentView.findViewById(R.id.details_ingredients)).setText(recipe.getRecipeIngredients());
        ((TextView)contentView.findViewById(R.id.details_time)).setText("" + recipe.getRecipeTime());
        ((TextView)contentView.findViewById(R.id.details_instructions)).setText(recipe.getRecipeInstructions());
        ((TextView)contentView.findViewById(R.id.details_date)).setText(recipe.getRecipeDate());
        ((TextView)contentView.findViewById(R.id.details_likes)).setText("" + recipe.getRecipeLikes());
        ((TextView)contentView.findViewById(R.id.details_category)).setText(recipe.getRecipeCategory());
        UserFirebase.getUser(recipe.getRecipeAuthorId(), new BaseInterface.GetUserCallback() {
            @Override
            public void onComplete(User user) {
                if(user.getUserImage() != null && !user.getUserImage().isEmpty() && !user.getUserImage().equals("")) {
                    ((ImageView) contentView.findViewById(R.id.details_author_image)).setImageBitmap(ModelFiles.loadImageFromFile(URLUtil.guessFileName(user.getUserImage(), null, null)));
                } else {
                    ((ImageView) contentView.findViewById(R.id.details_author_image)).setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.bakeitlogo));
                }
            }

            @Override
            public void onCancel() {

            }
        });
        if(recipe.getRecipeImage() != null && !recipe.getRecipeImage().isEmpty() && !recipe.getRecipeImage().equals("")) {
            ((ImageView) contentView.findViewById(R.id.details_image)).setImageBitmap(ModelFiles.loadImageFromFile(URLUtil.guessFileName(recipe.getRecipeImage(), null, null)));
        } else {
            ((ImageView) contentView.findViewById(R.id.details_image)).setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.bakeitlogo));
        }
        // Inflate the layout for this fragment
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}