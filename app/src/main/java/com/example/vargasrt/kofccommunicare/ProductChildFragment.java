package com.example.vargasrt.kofccommunicare;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ProductChildFragment extends Fragment {

    private static final String ARGUMENT_POSITION = "argument_position";
    public ImageView imageProduct1;
    public ImageView imageProduct2;
    public ImageView imageProduct3;
    public ImageView imageProduct4;

    public static ProductChildFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POSITION, position);
        ProductChildFragment fragment = new ProductChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_child, container, false);
    }


    @SuppressLint("SetTextI18n")
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TextView tvProduct = view.findViewById(R.id.tv_product);
        int position = getArguments().getInt(ARGUMENT_POSITION, -1);
        //tvProduct.setText(position == 0 ? R.string.do_not_stop_believing : R.string.life_is_a_very_funny_proposition_after_all);
        //tvProduct.setText(position == 0 ? R.string.KCFAPI_Mission : R.string.life_is_a_very_funny_proposition_after_all);
        switch (position){
            case 0:
                //tvProduct.setText(R.string.Protection);
                String imageUrlKCSpecialPlanElderlyKnights = getURLForResource(R.drawable.kcspecialplanelderlyknights);
                imageProduct1 = (ImageView) view.findViewById(R.id.image_product1);
                Glide.with(getContext())
                        .load(imageUrlKCSpecialPlanElderlyKnights)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct1);
                String imageUrlFamilyProtect = getURLForResource(R.drawable.kcfamilyprotect);
                imageProduct2 = (ImageView) view.findViewById(R.id.image_product2);
                Glide.with(getContext())
                        .load(imageUrlFamilyProtect)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct2);
                String imageUrlTermProtect = getURLForResource(R.drawable.termprotect5);
                imageProduct3 = (ImageView) view.findViewById(R.id.image_product3);
                Glide.with(getContext())
                        .load(imageUrlTermProtect)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct3);
                String imageUrlKCPrimeShield = getURLForResource(R.drawable.kcprimeshield);
                imageProduct4 = (ImageView) view.findViewById(R.id.image_product4);
                Glide.with(getContext())
                        .load(imageUrlKCPrimeShield)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct4);
                return;
            case 1:
                //tvProduct.setText(R.string.Retirement);
                String imageUrlKCElitePro = getURLForResource(R.drawable.kcelitepro);
                imageProduct1 = (ImageView) view.findViewById(R.id.image_product1);
                Glide.with(getContext())
                        .load(imageUrlKCElitePro)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct1);
                return;
            case 2:
                //tvProduct.setText(R.string.Savings);
                String imageUrlKCCapitalBuilder = getURLForResource(R.drawable.kccapitalbuilder);
                imageProduct1 = (ImageView) view.findViewById(R.id.image_product1);
                Glide.with(getContext())
                        .load(imageUrlKCCapitalBuilder)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct1);
                String imageUrlKCArmorInvest = getURLForResource(R.drawable.kcarmorinvest);
                imageProduct2 = (ImageView) view.findViewById(R.id.image_product2);
                Glide.with(getContext())
                        .load(imageUrlKCArmorInvest)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct2);
                String imageUrlKCGEMSeries = getURLForResource(R.drawable.kcgemseries);
                imageProduct3 = (ImageView) view.findViewById(R.id.image_product3);
                Glide.with(getContext())
                        .load(imageUrlKCGEMSeries)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct3);
                String imageUrlKCCollegeSavings = getURLForResource(R.drawable.kccollegesavings);
                imageProduct4 = (ImageView) view.findViewById(R.id.image_product4);
                Glide.with(getContext())
                        .load(imageUrlKCCollegeSavings)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct4);
                return;
            case 3:
                //tvProduct.setText(R.string.Investment);
                String imageUrlKCUSDollarSaver = getURLForResource(R.drawable.kcusdollarsaver);
                imageProduct1 = (ImageView) view.findViewById(R.id.image_product1);
                Glide.with(getContext())
                        .load(imageUrlKCUSDollarSaver)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct1);
                String imageUrlKCOTPPeso = getURLForResource(R.drawable.kcotppeso);
                imageProduct2 = (ImageView) view.findViewById(R.id.image_product2);
                Glide.with(getContext())
                        .load(imageUrlKCOTPPeso)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(imageProduct2);
                return;
        }
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}