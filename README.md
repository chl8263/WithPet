With pet
=============

:rocket: Purpose of program
-------------
'With pet' project is comprehensive dog app for pet owner.

WithPet provide trail path, pet shop, ownerless dog's information.


:factory: Program structure
-------------

### Technology set

|Technical Name|Value|
|:---:|:---:|
|Language|[Kotlin](https://kotlinlang.org/)|
|Pattern|[MVVM](https://medium.com/hongbeomi-dev/aac%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-mvvm-pattern%EC%9D%84-%EA%B5%AC%ED%98%84%ED%95%9C-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%95%B1-%EB%A7%8C%EB%93%A4%EA%B8%B0-1d6d73689bd0)|
|Rest Api|[Retrofit2](https://square.github.io/retrofit/)|
|DI|[Koin](https://github.com/InsertKoinIO/koin)|
|Reactive|[Rxjava](https://github.com/ReactiveX/RxJava), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata?hl=en)|
|BaaS (Backend as a Service)|[Firebase](https://firebase.google.com/)|
<br/>

![withpet structure](https://user-images.githubusercontent.com/23304953/102682291-c27d5800-420b-11eb-96ca-d8dc1de890f9.PNG)

In order to request Api on client need to put JWT token on 'Authorization' of http header.
__Client get JWT token at login and save JWT state at REDUX.__

<br/>

### MVVM pattern

MVVM pattern has benefit to seperate business, presentation logic from UI.
It clearly separates the business and UI, making it easier to test and easier to maintain.

#### View

View is responsible for the layout structure on shown screen. Also it can perform related UI logic.

#### ViewModel

The ViewModel implements data and commands in the View, and notifies the View of changes in state through change notification events. 
And the view that receives the status change notification will decide whether to apply the change or not.

~~~Koltin
class HosCommentViewModel(val hospitalCommentRepository: HospitalCommentRepository) : BaseViewModel() {

    private val _isPutComment = MutableLiveData<Boolean>()
    val isPutComment: LiveData<Boolean>
        get() = _isPutComment

    fun putHospitalComment(hospitalUid : String, review : HospitalReviewDTO) {
        addDisposable(
            hospitalCommentRepository.putHospitalComment(hospitalUid , review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {t: Boolean ->
                    _isPutComment.postValue(t)
                }
        )
        hospitalCommentRepository.putHospitalComment(hospitalUid , review)
    }

    fun putHospitalStar(hospitalUid : String , starPoint: Int) {
        hospitalCommentRepository.putHospitalStar(hospitalUid , starPoint)
    }
}
~~~

#### Model
Model is a non-visual class that holds the data you want to use. Examples include data transfer objects (DTOs), plain old Java objects (POJOs), or entity objects. 
Typically used with services or repositories that access data or need caching.

~~~Koltin
override fun getHospitalSearchData(searchValue: String): Observable<ArrayList<HospitalSearchDTO>> {
    return Observable.create {
        emitter ->
        firestore.collection(COLECT_HOSPITAL).orderBy(COLECT_HOSPITAL_NAME).startAt(searchValue).endAt(searchValue+'\uf8ff').get().addOnCompleteListener {
            task: Task<QuerySnapshot> ->

            if(task.isSuccessful) {
                list.clear()
                for(snapshot in task.result?.documents!!){
                    list.add(snapshot.toObject(HospitalSearchDTO::class.java)!!)
                }

                emitter.onNext(list)
            }

        }
    }
}
~~~

<br/>

:computer: UI Function
-------------

![ui1](https://user-images.githubusercontent.com/23304953/102683272-f60fb080-4212-11eb-920f-1c7fe6f5bf43.PNG)

![ui2](https://user-images.githubusercontent.com/23304953/102683275-f740dd80-4212-11eb-88f5-0d4c33110b3d.PNG)

![ui3](https://user-images.githubusercontent.com/23304953/102683276-f7d97400-4212-11eb-8779-424b48d58c3c.PNG)
