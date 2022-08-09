package com.example.androidtvapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.leanback.app.BrowseFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;

import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;

import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends BrowseFragment {


    private static final String TAG = MainFragment.class.getSimpleName();

    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    @SuppressLint("StaticFieldLeak")
    private static PicassoBackgroundManager picassoBackgroundManager = null;
    String description = "";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        loadRows();

        setupEventListeners();

        picassoBackgroundManager = new PicassoBackgroundManager(getActivity());
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is clicked, code inside here will be executed.
            if (item instanceof Movie) {
                Movie movie = (Movie) item;
                Log.d(TAG, "Item: " + item.toString());
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.MOVIE, movie);

                getActivity().startActivity(intent);
            } else if (item instanceof String) {
                if (item == "ErrorFragment") {
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private static final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            // each time the item is selected, code inside here will be executed.
            if (item instanceof String) {
                // GridItemPresenter
                picassoBackgroundManager.updateBackgroundWithDelay("https://cdn.hobbyconsolas.com/sites/navi.axelspringer.es/public/styles/1200/public/media/image/2018/10/sword-art-online-ordinal-scale.jpg?itok=rFmwcaYB");
            } else if (item instanceof Movie) {
                // CardPresenter
                picassoBackgroundManager.updateBackgroundWithDelay(((Movie) item).getCardImageUrl());
            }
        }
    }

    private void setupUIElements() {
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void loadRows() {
        ArrayObjectAdapter mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        /* GridItemPresenter */
        HeaderItem gridItemPresenterHeader = new HeaderItem(0, "Movies");

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(mGridPresenter);
        /*gridRowAdapter.add("ErrorFragment");*/
        gridRowAdapter.add("Proximamente...");
        gridRowAdapter.add("Proximamente...");
        mRowsAdapter.add(new ListRow(gridItemPresenterHeader, gridRowAdapter));

        /* CardPresenter */
        HeaderItem cardPresenterHeader = new HeaderItem(1, "Series");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();

            if (i == 0) {
                movie.setCardImageUrl("https://nntheblog.com/wp-content/uploads/2022/04/Overlord-5.jpg");
                movie.setTitle("Overlord");
                movie.setStudio("Madhouse");
                description = "Un juego de rol en línea adquiere una realidad propia después de que sus servidores se apagan permanentemente y atrapa al único jugador que queda conectado, quien lucha por mantener el frágil equilibrio entre las razas del mundo.";

            } else if (i == 1) {
                movie.setCardImageUrl("https://cdn.wallpapersafari.com/25/4/8IROwD.jpg");
                movie.setTitle("To Love RU");
                movie.setStudio("Kentaro Yabuki y Saki Hasemi");
                description = "La historia se desarrolla alrededor de Rito Yūki, un estudiante de preparatoria que está profundamente enamorado de Haruna Sairenji desde la secundaria y a quien no puede expresar sus sentimientos.";

            } else if (i == 2) {
                movie.setCardImageUrl("https://i.blogs.es/5d44f5/ataque-a-los-titanes2/1366_2000.jpg");
                movie.setTitle("Shingeki no kyojin");
                movie.setStudio("Tetsurō Araki y producida por Wit Studio, en colaboración con Production I.G.");
                description = "Desde hace 100 años los titanes aparecieron de la nada y llevaron a la humanidad al borde de la extinción; la población vive encerrada en ciudades rodeadas de enormes muros. Eren Jaeger, un joven cansado del conformismo, sueña con el mundo exterior.";
            } else if (i == 3) {
                movie.setCardImageUrl("https://i0.wp.com/news.qoo-app.com/en/wp-content/uploads/sites/3/2022/06/uzaki_chan_season_2_pv1_feature.jpg");
                movie.setTitle("Uzaki-chan wa Asobitai!");
                movie.setStudio("ENGI");
                description = " El único deseo de Shinichi Sakurai es un poco de paz y tranquilidad. Pero Hana Uzaki, su animada y bien dotada compañera de instituto, tiene otros planes. Todo lo que ella quiere es pasar el rato y gastarle bromas.";
            } else {
                movie.setCardImageUrl("https://bilder.fernsehserien.de/sendung/hr2/gleipnir_700197.png");
                movie.setTitle("Gleipnir");
                movie.setStudio("Pine Jam");
                description = "Shuichi Kagaya es un chico ordinario de preparatoria en una pequeña y aburrida ciudad. Sin embargo, cuando una chica se ve atrapada en un incendio en un almacén, Shuichi usa su poder misterioso, transformarse en un perro peludo de gran tamaño con un enorme revólver y una cremallera en la espalda. Él salva la vida de la chica, quien decide guardar su secreto. Pero ella se encuentra buscando a su hermana, quien mató a su familia, y no le importará lo degradante que sea: usará a Shuichi para cumplir su misión.";
            }


            movie.setDescription(description);
            cardRowAdapter.add(movie);
        }

        mRowsAdapter.add(new ListRow(cardPresenterHeader, cardRowAdapter));

        /* Set */
        setAdapter(mRowsAdapter);
    }


    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }
}