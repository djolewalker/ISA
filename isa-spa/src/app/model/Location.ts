export type Coordinates = [number, number][];

export type ISALocation = {
  longitude: number;
  latitude: number;
};

export type DriverLocation = {
  id: number;
  occupied: boolean;
} & ISALocation;
