package com.developer.currency.ui.widget

//@HiltViewModel
//class WidgetViewModel @Inject constructor(private val valuteUseCase: com.developer.domain.usecases.ValuteUseCase) : ViewModel() {
//    fun getLocalValutes(): Flow<List<Valute>> = valuteUseCase.getLocalValutes()
//
//    private val _getLocalValuteById = MutableStateFlow<Valute?>(null)
//    val getLocalValuteById: StateFlow<Valute?> get() = _getLocalValuteById
//    fun getLocalValuteById(valId: Int) = viewModelScope.launch {
//        _getLocalValuteById.value = valuteUseCase.getLocalValuteById(valId)
//    }
//}