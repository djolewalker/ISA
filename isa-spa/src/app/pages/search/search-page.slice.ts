import { PayloadAction, createSelector, createSlice } from '@reduxjs/toolkit';
import { RouteLocations } from 'app/model/Route';
import { RootState } from 'app/redux/store';
import { OSMLocation } from 'app/service/locations-service';

export type SearchFormFields = 'start' | 'destination';
type FieldKey = string | number;
type FieldSuggestions = { [key: FieldKey]: OSMLocation[] };

export type SearchState = {
  activeField: SearchFormFields | null;
  locationsQuery: string;
  suggestions: FieldSuggestions;
  selectedLocations: Partial<RouteLocations>;
};

const initialState: SearchState = {
  activeField: null,
  locationsQuery: '',
  suggestions: {},
  selectedLocations: {}
};

const searchPage = createSlice({
  name: 'searchPage',
  initialState,
  reducers: {
    setLocationsQuery: (state, { payload }: PayloadAction<string>) => {
      if (!state.activeField) return;

      state.locationsQuery = payload;
      state.suggestions[state.activeField] = [];
    },
    locationReceived: (state, { payload }: PayloadAction<OSMLocation[]>) => {
      if (!state.activeField) return;

      state.suggestions[state.activeField] = payload;
    },
    setSelectedLocations: (state, { payload }: PayloadAction<Partial<RouteLocations>>) => {
      state.selectedLocations = { ...state.selectedLocations, ...payload };
    },
    setActiveField: (state, { payload }: PayloadAction<Partial<SearchFormFields | null>>) => {
      state.activeField = payload;
    }
  }
});

const searchPageSliceSelector = (state: RootState) => state.searchPage;

export const selectLocationQuery = createSelector(searchPageSliceSelector, ({ locationsQuery }) => locationsQuery);
export const selectSuggestions = createSelector(searchPageSliceSelector, ({ suggestions }) => suggestions);
export const selectSelectedLocations = createSelector(
  searchPageSliceSelector,
  ({ selectedLocations }) => selectedLocations as RouteLocations
);
export const selectActiveField = createSelector(searchPageSliceSelector, ({ activeField }) => activeField);

export const { setLocationsQuery, locationReceived, setSelectedLocations, setActiveField } = searchPage.actions;

export default searchPage.reducer;
