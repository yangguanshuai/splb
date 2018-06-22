    /*********************************************文件上传***************************************************/

    private void uploadeDo() {
        File file = new File("/storage/emulated/0/Download/11.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file_name", file.getName(), new ProgressRequestBody
                (requestBody,
                        new UploadProgressListener() {
                            @Override
                            public void onProgress(final long currentBytesCount, final long totalBytesCount) {

                                /*回到主线程中，可通过timer等延迟或者循环避免快速刷新数据*/
                                Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {

                                    @Override
                                    public void call(Long aLong) {
                                        tvMsg.setText("提示:上传中");
                                        progressBar.setMax((int) totalBytesCount);
                                        progressBar.setProgress((int) currentBytesCount);
                                    }
                                });
                            }
                        }));
        UploadApi uplaodApi = new UploadApi(httpOnNextListener, this);
        uplaodApi.setPart(part);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(uplaodApi);
    }


    /**
     * 上传回调
     */
    HttpOnNextListener httpOnNextListener = new HttpOnNextListener<UploadResulte>() {
        @Override
        public void onNext(UploadResulte o) {
            tvMsg.setText("成功");
            Glide.with(MainActivity.this).load(o.getHeadImgUrl()).skipMemoryCache(true).into(img);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            tvMsg.setText("失败：" + e.toString());
        }

    };