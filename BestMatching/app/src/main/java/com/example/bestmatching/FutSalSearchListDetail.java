package com.example.bestmatching;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.Nullable;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

public class FutSalSearchListDetail extends Fragment implements View.OnClickListener {

    private Context context;

    TextView detail_name;
    TextView detail_price;
    ImageView detail_ground;

    Button back_btn;
    Button book_btn;

    String name;
    String price;

    private int stuck = 10;

    public static FutSalSearchListDetail newInstance() {
        return new FutSalSearchListDetail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        View view = inflater.inflate(R.layout.fragment_futsal_search_list_item_detail, null);

        context = container.getContext();

        detail_name = (TextView)view.findViewById(R.id.detail_name);
        detail_price = (TextView)view.findViewById(R.id.detail_price);
        detail_ground = (ImageView)view.findViewById(R.id.detail_ground);


        name = getArguments().getString("name");
        price = getArguments().getString("price");
        //Toast.makeText(getActivity(),Integer.toString(a),Toast.LENGTH_SHORT).show();

        detail_name.setText(name);
        detail_price.setText(price + "원");

        int id = getArguments().getInt("id");
        switch (id) {
            case 1:
                Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.sangju);
                detail_ground.setImageBitmap(bm1);
                break;
            case 2:
                Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.daegu);
                detail_ground.setImageBitmap(bm2);
                break;
        }

        back_btn = (Button)view.findViewById(R.id.back_btn);
        book_btn = (Button)view.findViewById(R.id.book_btn);

        back_btn.setOnClickListener(this);
        book_btn.setOnClickListener(this);

        BootpayAnalytics.init(context, "5f6c1743878a56001dffad61");

        return view;
    }

    @Override
    public void onClick(View v) {
        int a = v.getId();
        switch (a) {
            case R.id.back_btn:
                ((MainActivity)getActivity()).backFragment(FutSalSearchActivity.newInstance(), FutSalSearchListFragment.newInstance());
                break;
            case R.id.book_btn:
                BootUser bootUser = new BootUser().setPhone("010-1234-5678");
                BootExtra bootExtra = new BootExtra().setQuotas(new int[] {0,2,3});

                Bootpay.init(getActivity().getFragmentManager())
                        .setApplicationId("5f6c1743878a56001dffad61") // 해당 프로젝트(안드로이드)의 application id 값
                    .setPG(PG.KCP) // 결제할 PG 사
                    .setMethod(Method.KAKAO) // 결제수단
                    .setContext(context)
                    .setBootUser(bootUser)
                    .setBootExtra(bootExtra)
                    .setUX(UX.PG_DIALOG)
                    .setName(name) // 결제할 상품명
                    .setOrderId("1234") // 결제 고유번호
                    .setPrice(Integer.parseInt(price)) // 결제할 금액
                    //.addItem("마우스", 1, "ITEM_CODE_MOUSE", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
                    //.addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
                    .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                        @Override
                        public void onConfirm(@Nullable String message) {
                            if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                            else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                            Log.d("confirm", message);
                        }
                    })
                    .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                        @Override
                        public void onDone(@Nullable String message) {
                            Log.d("done", message);
                        }
                    })
                    .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                        @Override
                        public void onReady(@Nullable String message) {
                            Log.d("ready", message);
                        }
                    })
                    .onCancel(new CancelListener() { // 결제 취소시 호출
                        @Override
                        public void onCancel(@Nullable String message) {

                            Log.d("cancel", message);
                        }
                    })
                    .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                        @Override
                        public void onError(@Nullable String message) {
                            Log.d("error", message);
                        }
                    })
                    .onClose(
                            new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                @Override
                                public void onClose(String message) {
                                    Log.d("close", "close");
                                }
                            })
                    .request();
                break;
        }

    }

}
