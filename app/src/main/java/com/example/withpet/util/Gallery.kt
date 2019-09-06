package com.example.withpet.util

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception


object Gallery {

    const val REQUEST_CALL_GALLERY = 1003
    const val REQUEST_CROP = 1005
    const val IMAGE_PREFIX = "IMG_"
    const val PNG = ".png"
    const val SCALED_PREFIX = "scaled_"
    const val COVER_PREFIX = "cover_"

    // uri 저장 LG, Bey 폰등 이상현상 으로 인해서 선언
    var mResultUri: Uri? = null

    fun getImgDirectory(context: Context): File {
        val tempDirectory = File("${context.filesDir.path}/recentImg/")
        if (!tempDirectory.exists()) tempDirectory.mkdirs()
//        return tempDirectory
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: tempDirectory
    }

    // =========== activity
    /**
     * 갤러리 호출
     */
    fun callGallery(activity: Activity, requestCode: Int = REQUEST_CALL_GALLERY) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        activity.startActivityForResult(photoPickerIntent, requestCode)
    }

    fun callGallery(fragment: Fragment, requestCode: Int = REQUEST_CALL_GALLERY) {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        fragment.startActivityForResult(photoPickerIntent, requestCode)
    }

    /**
     * 이미지 크롭
     */
    fun startCrop(activity: Activity, pathFile: Uri, fileName: String, requestCode: Int = REQUEST_CROP) {
        val cropIntent = getCropIntent(activity, pathFile, fileName)
        cropIntent?.let { activity.startActivityForResult(it, requestCode) }
    }

    fun startCrop(activity: Activity, fragment: Fragment, pathFile: Uri, fileName: String, requestCode: Int = REQUEST_CROP) {
        val cropIntent = getCropIntent(activity, pathFile, fileName)
        cropIntent?.let { fragment.startActivityForResult(it, requestCode) }
    }

    fun getCropIntent(activity: Activity, pathFile: Uri, fileName: String): Intent? {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(pathFile, "image/*")
        val list = activity.packageManager.queryIntentActivities(intent, 0)
        val size = list.size
        if (size == 0) {
            return null
        } else {
            intent.putExtra("crop", "true")
            intent.putExtra("aspectX", 1) // 비율
            intent.putExtra("aspectY", 1)
            intent.putExtra("scale", true)

//            val file = File.createTempFile(fileName, PNG, getImgDirectory(activity))
            File.createTempFile(fileName, PNG, getImgDirectory(activity))
            val file = File.createTempFile(fileName, PNG, activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
//            mResultUri = Uri.fromFile(createImageFile(prefix = IMAGE_PREFIX, activity = activity))
            mResultUri = Uri.fromFile(file)

            intent.putExtra("return-data", false)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mResultUri)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.name) //Bitmap 형태로 받기 위해 해당 작업 진행

            val i = Intent(intent)
            val res = list[0]
            i.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            return i
        }
    }

    fun getCropIntent(ap: Application, pathFile: Uri): Intent? {
        val applicationContext = ap.applicationContext
        val cropIntent = Intent("com.android.camera.action.CROP").apply {
            setDataAndType(pathFile, "image/*")
        }
        val cropList = applicationContext.packageManager.queryIntentActivities(cropIntent, 0)
        if (cropList.isEmpty()) return null

        val file = File.createTempFile("test", PNG, applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        val resultUri = Uri.fromFile(file)

        cropIntent.apply {
            putExtra("crop", "true")
            putExtra("aspectX", 16) // 비율
            putExtra("aspectY", 9)
            putExtra("return-data", true)
            putExtra("scale", true)
            putExtra("outputFormat", Bitmap.CompressFormat.PNG.name) //Bitmap 형태로 받기 위해 해당 작업 진행
            putExtra(MediaStore.EXTRA_OUTPUT, resultUri)
        }

        return Intent(cropIntent).apply {
            val resolveInfo = cropList[0]
            component = ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name)
        }
    }


    // =========== util
    /**
     * uri to path
     */
    fun getRealPathFromURI(activity: Activity, contentUri: Uri): String? {
        Log.w("path = ${contentUri.path}")
        if (contentUri.path?.startsWith("/storage") == true) {
            return contentUri.path
        }
        var cursor: Cursor? = null
        try {
            cursor = activity.contentResolver.query(contentUri, null, null, null, null)
            cursor!!.moveToNext()
            return File(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))).path
        } finally {
            cursor?.close()
        }
        return null
    }


    /**
     * 사진 리사이징
     */
    fun scaleImage(path: String, fileName: String, activity: Activity, maxSize: Int = 500): File? {
        return scaleImage(File(path), fileName, activity, maxSize)
    }

    fun scaleImage(file: File?, fileName: String, activity: Activity, maxSize: Int = 500): File? {
        if (file == null)
            return null
        val imageStream = activity.contentResolver.openInputStream(Uri.fromFile(file))
        var selectedImage: Bitmap? = null
        while (selectedImage == null) {
            var options: BitmapFactory.Options? = null
            try {
                selectedImage = BitmapFactory.decodeStream(imageStream, null, options)
            } catch (e: Exception) {
                if (options == null) {
                    options = BitmapFactory.Options()
                    options.inSampleSize = 1
                }
                options.inSampleSize *= 2
            }
        }

        var width = selectedImage.width
        var height = selectedImage.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }

        val rotate = getPhotoOrientation(activity, file.absolutePath)
        val matrix = Matrix()
        matrix.postRotate(rotate?.toFloat() ?: 0F)

//        val dstFile = File.createTempFile(fileName, PNG, getImgDirectory(activity))
        val dstFile = File.createTempFile(fileName, PNG, activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        if (!dstFile.exists()) dstFile.createNewFile()
        val fileOutputStream = FileOutputStream(dstFile)
        val bitmap1 = Bitmap.createScaledBitmap(selectedImage, width, height, true)
        val bitmap = Bitmap.createBitmap(bitmap1, 0, 0, width, height, matrix, true)
        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        return dstFile
    }

    fun getPhotoOrientation(context: Context, path: String): Int? {
        // DATA는 이미지 파일의 스트림 데이터 경로를 나타냅니다.
        val projection = arrayOf(MediaStore.Images.Media.ORIENTATION)
        val cursor: Cursor?

        cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 이미지 컨텐트 테이블
                projection, MediaStore.Images.Media.DATA + " LIKE ? ", arrayOf("%$path%"), null
        )// DATA를 출력

        cursor?.let { cursor ->

            val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION)

            if (cursor.moveToFirst()) {
                val taken = cursor.getInt(dataColumnIndex)

                cursor.close()
                return taken
            }
            cursor.close()
        }
        return -1
    }

    /**
     * 파일 생성
     * default prefix = ""
     * default path = app 내부 directory(갤러리 비공개)
     */
    fun createImageFile(prefix: String = "", path: File? = null, activity: Activity): File? {
        if (path != null) {
            if (!path.exists()) {
                path.mkdir()
            }
        }

//        val storageDir: File = path ?: getImgDirectory(activity)
        val storageDir: File = path ?: activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        var image: File? = null
        try {
            image = File.createTempFile(
                    //@formatter:off
                    prefix, /* prefix */
                    ".jpg", /* suffix */
                    storageDir       /* directory */
                    //@formatter:on
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return image
    }

    /**
     * 폴더 속 이미지 전체 삭제
     */
    fun deleteAllFiles(prefix: String = "", dirPath: String? = null, activity: Activity) {
//        val filePath = dirPath ?: getImgDirectory(activity).path
        val filePath = dirPath ?: activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path
        val list = File(filePath)

        if (list.exists()) {
            //해당 폴더의 전체 파일리스트 조회
            val fileList = list.list()

            //전체파일
            fileList?.let {
                for (i in 0 until fileList.size) {
                    //파일명 조회
                    val fileName = fileList[i]

                    //파일명에 eid 값이 존재하는지 체크
                    if (fileName.startsWith(prefix)) {
                        //존재하면 파일삭제
                        val deleteFile = File("$filePath/$fileName")
                        deleteFile.delete()
                    }
                }
            }
        }
    }
}
